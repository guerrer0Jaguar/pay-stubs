module com.guerrer0jaguar.paystubs.daosimpl {
	requires com.guerrer0jaguar.paystubs.entity;
	requires com.guerrer0jaguar.paystubs.dao;
	requires software.amazon.awssdk.regions;
	requires transitive software.amazon.awssdk.utils;
	requires transitive software.amazon.awssdk.awscore;
	requires transitive software.amazon.awssdk.core;
	requires transitive software.amazon.awssdk.services.dynamodb;
	requires transitive software.amazon.awssdk.enhanced.dynamodb;	
	provides com.guerrer0jaguar.paystubs.dao.Dao with com.guerrer0jaguar.paystubs.daosimpl.PayStubDao;
	provides com.guerrer0jaguar.paystubs.dao.DaoProviderFactory with com.guerrer0jaguar.paystubs.daosimpl.PayStubDaoProvider;
	exports com.guerrer0jaguar.paystubs.daosimpl;
}