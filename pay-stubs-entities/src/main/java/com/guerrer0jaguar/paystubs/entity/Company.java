package com.guerrer0jaguar.paystubs.entity;

import java.util.Objects;

import software.amazon.awssdk.enhanced.dynamodb.DefaultAttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbFlatten;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Company {

    private String taxId;// RFC
    private String name;
    private BaseEntity entity;

    @DynamoDbSortKey
    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(
            String taxId) {
        this.taxId = taxId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(
            String name) {
        this.name = name;
    }

    @DynamoDbFlatten
    public BaseEntity getEntity() {
        return entity;
    }

    public void setEntity(
            BaseEntity entity) {
        this.entity = entity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, name, taxId);
    }

    @Override
    public boolean equals(
            Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Company other = (Company) obj;
        return Objects.equals(entity, other.entity)
                && Objects.equals(name, other.name)
                && Objects.equals(taxId, other.taxId);
    }

    @Override
    public String toString() {
        return "Company [taxId=" + taxId + ", name=" + name + ", entity="
                + entity + "]";
    }
}