module com.guerrer0jaguar.paystubs.daosimpl {
	requires com.guerrer0jaguar.paystubs.entity;
	requires com.guerrer0jaguar.paystubs.dao;
	requires software.amazon.awssdk.enhanced.dynamodb;
	provides com.guerrer0jaguar.paystubs.dao.Dao with com.guerrer0jaguar.paystubs.daosimpl.PayStubDao;
	exports com.guerrer0jaguar.paystubs.daosimpl;
}