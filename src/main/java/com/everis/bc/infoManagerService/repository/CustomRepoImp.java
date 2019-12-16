package com.everis.bc.infoManagerService.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.everis.bc.infoManagerService.model.CuentaCorrienteP;
import com.everis.bc.infoManagerService.model.ProductDetails;

import reactor.core.publisher.Mono;
@Repository
@Transactional(readOnly = true)
public class CustomRepoImp implements CustomRepo{
	
	@Autowired
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public CustomRepoImp(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public Mono<ProductDetails> updateCcpByDoc(String doc, CuentaCorrienteP ccp) {
		// TODO Auto-generated method stub
		return null;
	}

}
