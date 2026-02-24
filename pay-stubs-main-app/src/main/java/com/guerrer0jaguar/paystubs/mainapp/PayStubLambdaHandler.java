package com.guerrer0jaguar.paystubs.mainapp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.guerrer0jaguar.paystubs.dao.Dao;
import com.guerrer0jaguar.paystubs.dao.DaoProviderFactory;
import com.guerrer0jaguar.paystubs.entity.PayStub;
import com.guerrer0jaguar.paystubs.rendering.PayStubRendering;
import com.guerrer0jaguar.paystubs.rendering.impl.PayStubRenderingPDFimpl;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Slf4j
public class PayStubLambdaHandler implements RequestHandler<PayStub, Response> {

    private final Dao<PayStub, Key> daoService;
    private final PayStubRendering render;
    private final S3Client s3Client;

    public PayStubLambdaHandler() {        
        
        daoService = loadDAOservice();
        render = loadRenderService();
        s3Client = S3Client.builder().build();

        String pdfBucket = System.getenv("PDF_BUCKET");
        if (Objects.isNull(pdfBucket) || pdfBucket.isBlank()) {
            throw new IllegalArgumentException(
                    "PDF_BUCKET variable is no set!!");
        }
    }

    @Override
    public Response handleRequest(
            PayStub input,
            Context context) {

        boolean isInputValid = render.isPayStubValid(input);
        String id = UUID.randomUUID().toString();
        input.setId(id);

        if (!isInputValid) {
            return new Response("Request invalid!");
        }

        try {
            byte[] pdf = render.generatePayStubRepresentation(input);
            final String publicUrl = saveFileInS3Bucket(input, pdf);
            input.setUrlFile(publicUrl);
        } catch (IOException e) {
            String msg = "Error at creating PDF...";
            log.error(msg, e);
            return new Response(msg);
        }

        daoService.save(input);

        return new Response("Request received processin...");
    }

    private String saveFileInS3Bucket(
            PayStub input,
            byte[] fileBytes) {

        InputStream pdfInputStream = new ByteArrayInputStream(fileBytes);
        String fileName = input.getId();
        PutObjectRequest putObjectRequest = PutObjectRequest
                .builder()
                .bucket(getBucketToSavePDF())
                .key(fileName)
                .build();

        RequestBody requestBody = RequestBody
                .fromInputStream(pdfInputStream, fileBytes.length);
        PutObjectResponse resp = s3Client
                .putObject(putObjectRequest, requestBody);
        log.info("Image saved in S3 bucket: {}", resp);

        final String publicUrl = String
                .format("https://%s.s3.%s.amazonaws.com/%s",
                        getBucketToSavePDF(), System.getenv("AWS_REGION"),
                        fileName);

        return publicUrl;
    }
    
    @SuppressWarnings("unchecked")
    private Dao<PayStub, Key> loadDAOservice() {
        Iterable<DaoProviderFactory> services = ServiceLoader
                .load(DaoProviderFactory.class);
        DaoProviderFactory factory = services.iterator().next();

        return (Dao<PayStub, Key>) factory.createDao();
    }
    
    private PayStubRendering loadRenderService() {
        return new PayStubRenderingPDFimpl();        
    }

    String getBucketToSavePDF() {
        return System.getenv("PDF_BUCKET");
    }


}