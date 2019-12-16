package com.everis.bc.infoManagerService.service;


import java.util.Map;

import com.everis.bc.infoManagerService.model.CreditoTC;
import com.everis.bc.infoManagerService.model.CuentaCorrienteE;
import com.everis.bc.infoManagerService.model.ProductDetails;
import com.everis.bc.infoManagerService.model.ResponseDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InfoManagerService {
	
	
	public Mono<ResponseDto> getDataByDocP(String doc);
	
	public Mono<ResponseDto> getDataByDocE(String doc);
}
