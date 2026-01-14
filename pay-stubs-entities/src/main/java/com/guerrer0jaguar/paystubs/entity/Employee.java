package com.guerrer0jaguar.paystubs.entity;

import java.util.Objects;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbFlatten;

public class Employee {

    private String taxId;// RFC
    private String firstName;
    private String lastName;
    private BaseEntity entity;
    private Company company;

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @DynamoDbFlatten
    public BaseEntity getEntity() {
        return entity;
    }

    public void setEntity(BaseEntity entity) {
        this.entity = entity;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, entity, firstName, lastName, taxId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Employee other = (Employee) obj;
        return Objects.equals(company, other.company)
                && Objects.equals(entity, other.entity)
                && Objects.equals(firstName, other.firstName)
                && Objects.equals(lastName, other.lastName)
                && Objects.equals(taxId, other.taxId);
    }

    @Override
    public String toString() {
        return "Employee [taxId=" + taxId + ", firstName=" + firstName
                + ", lastName=" + lastName + ", entity=" + entity + ", company="
                + company + "]";
    }
}