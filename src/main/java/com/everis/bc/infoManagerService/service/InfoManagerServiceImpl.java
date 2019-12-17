package com.everis.bc.infoManagerService.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.bc.infoManagerService.model.CreditoTC;
import com.everis.bc.infoManagerService.model.CuentaAhorro;
import com.everis.bc.infoManagerService.model.CuentaCorrienteE;
import com.everis.bc.infoManagerService.model.CuentaCorrienteP;
import com.everis.bc.infoManagerService.model.ProductDetails;
import com.everis.bc.infoManagerService.model.ResponseDto;
import com.everis.bc.infoManagerService.model.SaldosDto;
import com.everis.bc.infoManagerService.repository.Repo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class InfoManagerServiceImpl implements InfoManagerService {

	// private WebClient client;
	@Autowired
	WebClient client;
	@Autowired
	private Repo repo1;

	@Override
	public Mono<ResponseDto> getDataByDocP(String doc) {
		// TODO Auto-generated method stub
		// Map<String, Object> respuesta = new HashMap<String, Object>();
		List<CreditoTC> tdc = new ArrayList<CreditoTC>();
		// CreditoTC tdc1=new CreditoTC();
		// tdc.add(tdc1);
		CuentaCorrienteP cc = new CuentaCorrienteP();
		CuentaAhorro ca = new CuentaAhorro();
		// Mono<CuentaCorrienteP> cc2;
		ProductDetails pd = new ProductDetails();

		pd.setDoc(doc);
		pd.setCcp(cc);
		pd.setTc(tdc);
		pd.setCa(ca);
		repo1.save(pd).subscribe();

		ResponseDto respuesta = new ResponseDto();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("doc", doc);

		return client.get().uri("/ahorro/getCAhorroData/{doc}", params).accept(MediaType.APPLICATION_JSON_UTF8)
				.retrieve().bodyToMono(CuentaAhorro.class).flatMap(r -> {
					respuesta.setCa(r);
					return Mono.just(respuesta);
				}).switchIfEmpty(Mono.just(respuesta)).flatMap(p -> {
					return client.get().uri("/tc/getTCData/{doc}", params).accept(MediaType.APPLICATION_JSON_UTF8)
							.retrieve().bodyToFlux(CreditoTC.class).collectList().flatMap(o -> {
								respuesta.setTc(o);
								return Mono.just(respuesta);
							}).switchIfEmpty(Mono.just(respuesta)).flatMap(d -> {
								return client.get().uri("/pcorriente/getCcorrientePData/{doc}", params)
										.accept(MediaType.APPLICATION_JSON_UTF8).retrieve()
										.bodyToMono(CuentaCorrienteP.class).flatMap(r -> {
											respuesta.setCcp(r);
											return Mono.just(respuesta);
										}).switchIfEmpty(Mono.just(respuesta));
							});
				});

		/*
		 * return client.get().uri("/ahorro/getCAhorroData/{doc}",
		 * params).accept(MediaType.APPLICATION_JSON_UTF8)
		 * .retrieve().bodyToMono(CuentaAhorro.class).flatMap(r -> { if
		 * (r.getNro_cuenta().isEmpty()) { return Mono.just(pd); } else { return
		 * updateca(doc, r); } // return Mono.just(r); }).flatMap(p -> { return
		 * client.get().uri("/tc/getTCData/{doc}",
		 * params).accept(MediaType.APPLICATION_JSON_UTF8)
		 * .retrieve().bodyToFlux(CreditoTC.class).collectList().flatMap(r -> {
		 * 
		 * if (r.get(0).getDoc().isEmpty()) { return Mono.just(pd); } else { return
		 * updatetc(doc, r); } }).flatMap(d -> { return
		 * client.get().uri("/pcorriente/getCcorrientePData/{doc}", params)
		 * .accept(MediaType.APPLICATION_JSON_UTF8).retrieve()
		 * .bodyToMono(CuentaCorrienteP.class).flatMap(r -> { if
		 * (r.getNro_cuenta().isEmpty()) { return Mono.just(pd); } else { return
		 * updatecc(doc, r); } }).flatMap(q -> { return repo1.findByDoc(doc).flatMap(r
		 * -> { repo1.delete(r).subscribe(); return Mono.just(r); }); }); }); });
		 */

	}

	@Override
	public Mono<ResponseDto> getDataByDocE(String doc) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("doc", doc);
		ResponseDto pd = new ResponseDto();

		return client.get().uri("/ecorriente/getCcorrienteEData/{doc}", params).accept(MediaType.APPLICATION_JSON_UTF8)
				.retrieve().bodyToFlux(CuentaCorrienteE.class).collectList().flatMap(r -> {

					pd.setCce(r);
					return Mono.just(pd);
				}).switchIfEmpty(Mono.just(pd));
		// TODO Auto-generated method stub
		/*
		 * List<CuentaCorrienteE> cce=new ArrayList<CuentaCorrienteE>(); ProductDetails
		 * pd = new ProductDetails(); pd.setDoc(doc); pd.setCce(cce);
		 * repo1.findByDoc(doc).flatMap(d->repo1.delete(d)).subscribe();
		 * repo1.save(pd).subscribe(); Map<String, Object> params = new HashMap<String,
		 * Object>(); params.put("doc", doc); return
		 * client.get().uri("/ecorriente/getCcorrienteEData/{doc}",
		 * params).accept(MediaType.APPLICATION_JSON_UTF8)
		 * .retrieve().bodyToFlux(CuentaCorrienteE.class).collectList().flatMap(r -> {
		 * 
		 * if (r.get(0).getNro_cuenta().isEmpty()) { return Mono.just(pd); } else {
		 * return updatecce(doc, r); } }).flatMap(q -> { return
		 * repo1.findByDoc(doc).flatMap(r -> { repo1.delete(r).subscribe(); return
		 * Mono.just(r); }); });
		 */

	}

	public Mono<ProductDetails> updateca(String doc, CuentaAhorro ca) {
		return repo1.findByDoc(doc).flatMap(r -> {
			r.setCa(ca);
			return repo1.save(r);
		});

	}

	public Mono<ProductDetails> updatetc(String doc, List<CreditoTC> tc) {
		return repo1.findByDoc(doc).flatMap(r -> {
			r.setTc(tc);
			return repo1.save(r);
		});

	}

	public Mono<ProductDetails> updatecc(String doc, CuentaCorrienteP cc) {
		return repo1.findByDoc(doc).flatMap(r -> {
			r.setCcp(cc);
			return repo1.save(r);
		});

	}

	public Mono<ProductDetails> updatecce(String doc, List<CuentaCorrienteE> cce) {
		return repo1.findByDoc(doc).flatMap(r -> {
			r.setCce(cce);
			return repo1.save(r);
		});

	}

	@Override
	public Mono<List<SaldosDto>> getSaldosByDocP(String doc) {
		// SaldosDto respuesta=new SaldosDto();
		List<SaldosDto> response = new ArrayList<SaldosDto>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("doc", doc);

		return client.get().uri("/pcorriente/getCcorrientePData/{doc}", params).accept(MediaType.APPLICATION_JSON_UTF8)
				.retrieve().bodyToMono(CuentaCorrienteP.class).flatMap(r -> {
					SaldosDto respuesta = new SaldosDto();
					respuesta.setNumero(r.getNro_cuenta());
					respuesta.setProducto("Cuenta Corriente");
					respuesta.setSaldo(r.getSaldo());
					response.add(respuesta);
					return Mono.just(response);
				}).switchIfEmpty(Mono.just(response)).flatMap(p -> {
					return client.get().uri("/ahorro/getCAhorroData/{doc}", params)
							.accept(MediaType.APPLICATION_JSON_UTF8).retrieve().bodyToMono(CuentaAhorro.class)
							.flatMap(q -> {
								SaldosDto respuesta = new SaldosDto();
								respuesta.setNumero(q.getNro_cuenta());
								respuesta.setProducto("Cuenta Ahorro");
								respuesta.setSaldo(q.getSaldo());
								response.add(respuesta);
								return Mono.just(response);
							}).switchIfEmpty(Mono.just(response)).flatMap(m -> {
								return client.get().uri("/tc/getTCData/{doc}", params)
										.accept(MediaType.APPLICATION_JSON_UTF8).retrieve().bodyToFlux(CreditoTC.class)
										.collectList().flatMap(o -> {
											SaldosDto respuesta;
											for (CreditoTC h : o) {
												respuesta = new SaldosDto();
												respuesta.setNumero(h.getNro_tarjeta());
												respuesta.setProducto("Tarjeta Credito");
												respuesta.setSaldo(h.getSaldo());
												response.add(respuesta);
											}

											return Mono.just(response);
										})/*
											 * .switchIfEmpty(Mono.just(response)).flatMap(d -> { return
											 * client.get().uri("/pcorriente/getCcorrienteVipData/{doc}", params)
											 * .accept(MediaType.APPLICATION_JSON_UTF8).retrieve()
											 * .bodyToMono(CuentaCorrienteP.class).flatMap(n -> { SaldosDto
											 * respuesta=new SaldosDto(); respuesta.setNumero(n.getNro_cuenta());
											 * respuesta.setProducto(n.getTitulares().get(0).getProducto());
											 * respuesta.setSaldo(n.getSaldo()); response.add(respuesta); return
											 * Mono.just(response); }); })
											 */.switchIfEmpty(Mono.just(response)).flatMap(d -> {
											return client.get().uri("/ecorriente/getCcorrienteEData/{doc}", params)
													.accept(MediaType.APPLICATION_JSON_UTF8).retrieve().bodyToFlux(CuentaCorrienteE.class)
													.collectList().flatMap(o -> {
														SaldosDto respuesta;
														for (CuentaCorrienteE h : o) {
															respuesta = new SaldosDto();
															respuesta.setNumero(h.getNro_cuenta());
															respuesta.setProducto("Cuenta Corriente Empresas");
															respuesta.setSaldo(h.getSaldo());
															response.add(respuesta);
														}
														return Mono.just(response);
													}).switchIfEmpty(Mono.just(response));
										});
							});

				});
	}

}
