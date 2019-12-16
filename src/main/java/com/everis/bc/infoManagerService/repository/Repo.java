package com.everis.bc.infoManagerService.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.bc.infoManagerService.model.CuentaCorrienteP;
import com.everis.bc.infoManagerService.model.ProductDetails;

import reactor.core.publisher.Mono;

public interface Repo extends ReactiveMongoRepository<ProductDetails, String>{
	@Query("{ 'doc': ?0 }")
	public Mono<ProductDetails> findByDoc(String doc);
	
	@Query("{'doc' : ?0 }, {$set: {'ccp': ?1 }}")
	public Mono<ProductDetails> updateCcpByDoc(String doc, CuentaCorrienteP ccp);
	
	@Query("{ 'doc': ?0 }")
	public Mono<ProductDetails> deleteByDoc(String doc);
	
	
	//public boolean existByTitulares(String doc);
}
