package com.guerrer0jaguar.paystubs.entity;

import java.util.Objects;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class BaseEntity {
	private String id;
	private java.time.Instant creationDate;
	
	@DynamoDbPartitionKey
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public java.time.Instant getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(java.time.Instant creationDate) {
		this.creationDate = creationDate;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(creationDate, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		return Objects.equals(creationDate, other.creationDate) 
				&& Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "BaseEntity [id=" + id + ", creationDate=" + creationDate + "]";
	}
}