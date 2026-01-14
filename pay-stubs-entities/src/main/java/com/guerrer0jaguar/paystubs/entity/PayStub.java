package com.guerrer0jaguar.paystubs.entity;

import java.util.Objects;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbFlatten;

@DynamoDbBean
public class PayStub {
  private Employee employee;
  private String urlFile;
  private BaseEntity entity;

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public String getUrlFile() {
    return urlFile;
  }

  public void setUrlFile(String urlFile) {
    this.urlFile = urlFile;
  }

  @DynamoDbFlatten
  public BaseEntity getEntity() {
    return entity;
  }

  public void setEntity(BaseEntity entity) {
    this.entity = entity;
  }

  @Override
  public int hashCode() {
    return Objects.hash(employee, entity, urlFile);
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
    PayStub other = (PayStub) obj;
    return Objects.equals(employee, other.employee)
            && Objects.equals(entity, other.entity)
            && Objects.equals(urlFile, other.urlFile);
  }

  @Override
  public String toString() {
    return "PayStub [employee=" + employee + ", urlFile=" + urlFile
            + ", entity=" + entity + "]";
  }
  
}
