package com.everis.bc.infoManagerService.service;


import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;

import com.everis.bc.infoManagerService.model.ComisionesDto;
import com.everis.bc.infoManagerService.model.CreditoTC;
import com.everis.bc.infoManagerService.model.CuentaCorrienteE;
import com.everis.bc.infoManagerService.model.ProductDetails;
import com.everis.bc.infoManagerService.model.ResponseDto;
import com.everis.bc.infoManagerService.model.SaldosDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InfoManagerService {
	
	
	public Mono<ResponseDto> getDataByDocP(String doc);
	
	public Mono<ResponseDto> getDataByDocE(String doc);
	
	public Mono<List<SaldosDto>> getSaldosByDocP(String doc);
	
	public Mono<List<ComisionesDto>> getRangeComisionesByNro_cuenta(String nro_cuenta, String from, String to);
}
