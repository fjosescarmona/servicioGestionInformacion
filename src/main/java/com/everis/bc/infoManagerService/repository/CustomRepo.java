package com.everis.bc.infoManagerService.repository;

import com.everis.bc.infoManagerService.model.CuentaCorrienteP;
import com.everis.bc.infoManagerService.model.ProductDetails;

import reactor.core.publisher.Mono;

public interface CustomRepo {
	Mono<ProductDetails> updateCcpByDoc(String doc, CuentaCorrienteP ccp);
}
