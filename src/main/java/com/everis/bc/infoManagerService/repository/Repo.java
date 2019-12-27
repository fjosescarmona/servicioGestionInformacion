package com.everis.bc.infoManagerService.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.bc.infoManagerService.model.CuentaCorrienteP;
import com.everis.bc.infoManagerService.model.Deudores;
import com.everis.bc.infoManagerService.model.ProductDetails;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.lang.String;

public interface Repo extends ReactiveMongoRepository<Deudores, String>{
	@Query("{ 'documento': {$in:[ ?0 ]} }")
	public Flux<Deudores> findByDocumentoList(List<String> documento);
	
}
