module com.guerrer0jaguar.paystubs.mainapp {
    requires static lombok;
    requires static org.slf4j;
    
    requires com.guerrer0jaguar.paystubs.entity;
    requires com.guerrer0jaguar.paystubs.dao;
    requires com.guerrer0jaguar.paystubs.daosimpl;
    requires com.guerrer0jaguar.paystubs.rendering;
    requires com.guerrer0jaguar.paystubs.rendering.impl;
    requires aws.lambda.java.core;
    requires software.amazon.awssdk.services.s3;
    
    uses com.guerrer0jaguar.paystubs.rendering.PayStubRendering;
    uses com.guerrer0jaguar.paystubs.dao.DaoProviderFactory;
}