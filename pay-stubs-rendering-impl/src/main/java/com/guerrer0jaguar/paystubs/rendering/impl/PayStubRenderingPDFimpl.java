package com.guerrer0jaguar.paystubs.rendering.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

import org.openpdf.text.Chunk;
import org.openpdf.text.Document;
import org.openpdf.text.Element;
import org.openpdf.text.HeaderFooter;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.PdfName;
import org.openpdf.text.pdf.PdfString;
import org.openpdf.text.pdf.PdfWriter;

import com.guerrer0jaguar.paystubs.entity.PayStub;
import com.guerrer0jaguar.paystubs.rendering.PayStubRendering;

public class PayStubRenderingPDFimpl implements PayStubRendering {

    @SuppressWarnings("exports")
    @Override
    public byte[] generatePayStubRepresentation(
            PayStub payStub) throws IOException {
       
        if (!isPayStubValid(payStub) ){
            return defaultPDF();
        }
       
        byte [] result = null;
        Document document = new Document();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        
            PdfWriter pdf = PdfWriter.getInstance(document, baos);
            pdf.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
            document.addTitle(payStub.getId());
            
            HeaderFooter header = buildHeader(payStub, document);
            document.setHeader(header);

            HeaderFooter footer = buildFooter(payStub);
            document.setFooter(footer);
            
            document.open();
                                    
            Chunk name = new Chunk(payStub.getEmployee().getFullName());                                    
            document.add(name);
            
            Chunk total = new Chunk(formatTotal(payStub.getTotal() ));
            Paragraph pTotal = new Paragraph(total);
            pTotal.setAlignment(Element.ALIGN_RIGHT);
            document.add(pTotal);
            
            document.close();
            result = baos.toByteArray();
            
        }
        document.close();
        return result;
    }



    private HeaderFooter buildHeader(
            PayStub payStub,
            Document document) {
        String creationDate = formatCreationDate(payStub.getCreationDate());
        HeaderFooter header = new HeaderFooter(false, new Phrase(creationDate));
        header.setAlignment(Element.ALIGN_RIGHT);
        
        return header;
    }

    private String formatCreationDate(
            Instant date) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                .withZone(ZoneId.systemDefault());                
        
        return formatter.format(date);
    }

    private HeaderFooter buildFooter(
            PayStub payStub) {
        HeaderFooter footer = new HeaderFooter(true, new Phrase(payStub.getEmployee().getCompany().getName()) );
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setBorderWidthBottom(0);
        return footer;
    }    
    
    private String formatTotal(
            BigDecimal total) {
        
        NumberFormat formatter = NumberFormat.getCurrencyInstance();        
        return formatter.format(total);
    }

    @SuppressWarnings("exports")
    @Override
    public boolean isPayStubValid(PayStub payStub) {
        
        boolean isValid = PayStubRendering
                .super
                .isPayStubValid(payStub);
        
        if( !isValid) {
            return false;
        }
        
        if (Objects.isNull(payStub.getCreationDate()) ) {
            return false;
        }
        
        if (Objects.isNull(payStub.getTotal())  ) {
            return false;
        }
        
        return true;
    }
    
    private byte[] defaultPDF() throws IOException {
        try (InputStream in = loadFile("test1.pdf")) {
            return in.readAllBytes();
        }
    }
    
    InputStream loadFile(String filePath) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader.getResourceAsStream(filePath);
    }
}