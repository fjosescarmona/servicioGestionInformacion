package com.everis.bc.infoManagerService.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.everis.bc.infoManagerService.model.ComisionesDto;
import com.everis.bc.infoManagerService.model.CreditoTC;
import com.everis.bc.infoManagerService.model.CuentaCorrienteE;
import com.everis.bc.infoManagerService.model.Deudores;
import com.everis.bc.infoManagerService.model.ProductDetails;
import com.everis.bc.infoManagerService.model.ResponseDto;
import com.everis.bc.infoManagerService.model.SaldosDto;
import com.everis.bc.infoManagerService.service.InfoManagerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class InfoManagerController {

	@Autowired
	private InfoManagerService service;

	
	@GetMapping("/getClientPData/{doc}")
	public Mono<ResponseDto> getClientPData(@PathVariable("doc") String doc){
		return service.getDataByDocP(doc);
	}
	
	@GetMapping("/getClientEData/{doc}")
	public Mono<ResponseDto> getClientEData(@PathVariable("doc") String doc){
		return service.getDataByDocE(doc);
	}
	
	@GetMapping("/getClientPSaldos/{doc}")
	public Mono<List<SaldosDto>> getClientPSaldos(@PathVariable("doc") String doc){
		return service.getSaldosByDocP(doc);
	}
	
	@GetMapping("/getClientComisiones/{nro_cuenta}/{from}/{to}")
	public Mono<List<ComisionesDto>> getClientComisiones(@PathVariable("nro_cuenta") String nro_cuenta, @PathVariable("from") String from, @PathVariable("to") String to){
		
		return service.getRangeComisionesByNro_cuenta(nro_cuenta, from, to);
	}
	
	@GetMapping("/getDeudores")
	public Flux<Deudores> getDeudores(){
		
		return service.getDeudores();
	}
	
	@GetMapping("/getDeudoresDoc/{docs}")
	public Flux<Deudores> getDeudoresDoc(@PathVariable("docs") List<String> docs){
		
		return service.getDeudoresDoc(docs);
	}

}
