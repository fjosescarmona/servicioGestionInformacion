package com.everis.bc.infoManagerService.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.bc.infoManagerService.model.ComisionesDto;
import com.everis.bc.infoManagerService.model.CreditoTC;
import com.everis.bc.infoManagerService.model.CuentaAhorro;
import com.everis.bc.infoManagerService.model.CuentaCorrienteE;
import com.everis.bc.infoManagerService.model.CuentaCorrienteP;
import com.everis.bc.infoManagerService.model.CuentaCorrienteVip;
import com.everis.bc.infoManagerService.model.Deudores;
import com.everis.bc.infoManagerService.model.Movimientos;
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
	@Qualifier("gateway")
	private WebClient client;
	@Autowired
	private Repo repo1;
	@Autowired
	@Qualifier("vip")
	private WebClient vip;
	@Autowired
	@Qualifier("pcorriente")
	private WebClient pcorriente;
	@Autowired
	@Qualifier("ecorriente")
	private WebClient ecorriente;
	@Autowired
	@Qualifier("ahorro")
	private WebClient ahorro;
	@Autowired
	@Qualifier("tc")
	private WebClient tc;

	@Override
	public Mono<ResponseDto> getDataByDocP(String doc) {

		ResponseDto respuesta = new ResponseDto();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("doc", doc);

		return ahorro.get().uri("/getCAhorroData/{doc}", params)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.retrieve()
				.bodyToFlux(CuentaAhorro.class)
				.collectList()
				.flatMap(r -> {
					respuesta.setCa(r);
					return Mono.just(respuesta);
				}).switchIfEmpty(Mono.just(respuesta)).flatMap(p -> {
					return tc.get().uri("/getTCData/{doc}", params).accept(MediaType.APPLICATION_JSON_UTF8).retrieve()
							.bodyToFlux(CreditoTC.class).collectList().flatMap(o -> {
								respuesta.setTc(o);
								return Mono.just(respuesta);
							}).switchIfEmpty(Mono.just(respuesta)).flatMap(d -> {
								return pcorriente.get().uri("/getCcorrientePData/{doc}", params)
										.accept(MediaType.APPLICATION_JSON_UTF8).retrieve()
										.bodyToFlux(CuentaCorrienteP.class).collectList().flatMap(r -> {
											respuesta.setCcp(r);
											return Mono.just(respuesta);
										}).switchIfEmpty(Mono.just(respuesta)).flatMap(v -> {
											return ecorriente.get().uri("/getCcorrienteEData/{doc}", params)
													.accept(MediaType.APPLICATION_JSON_UTF8).retrieve()
													.bodyToFlux(CuentaCorrienteE.class).collectList().flatMap(r -> {

														respuesta.setCce(r);
														return Mono.just(respuesta);
													}).switchIfEmpty(Mono.just(respuesta)).flatMap(f -> {
														return vip.get().uri("/getCcorrienteVipData/{doc}", params)
																.accept(MediaType.APPLICATION_JSON_UTF8).retrieve()
																.bodyToFlux(CuentaCorrienteVip.class).collectList().flatMap(r -> {

																	respuesta.setCcvip(r);
																	return Mono.just(respuesta);
																}).switchIfEmpty(Mono.just(respuesta));
													});
										});
							});
				});


	}

	@Override
	public Mono<ResponseDto> getDataByDocE(String doc) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("doc", doc);
		ResponseDto pd = new ResponseDto();

		return ecorriente.get().uri("/getCcorrienteEData/{doc}", params).accept(MediaType.APPLICATION_JSON_UTF8)
				.retrieve().bodyToFlux(CuentaCorrienteE.class).collectList().flatMap(r -> {

					pd.setCce(r);
					return Mono.just(pd);
				}).switchIfEmpty(Mono.just(pd));
		
	}

	@Override
	public Mono<List<SaldosDto>> getSaldosByDocP(String doc) {
		// SaldosDto respuesta=new SaldosDto();
		List<SaldosDto> response = new ArrayList<SaldosDto>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("doc", doc);

		return pcorriente.get().uri("/getCcorrientePData/{doc}", params).accept(MediaType.APPLICATION_JSON_UTF8)
				.retrieve().bodyToFlux(CuentaCorrienteP.class).collectList().flatMap(r -> {
					SaldosDto respuesta;
					for (CuentaCorrienteP h : r) {
						respuesta = new SaldosDto();
						respuesta.setBanco(h.getBank());
					respuesta.setNumero(h.getNro_cuenta());
					respuesta.setProducto("Cuenta Corriente");
					respuesta.setSaldo(h.getSaldo());
					response.add(respuesta);
					}
					return Mono.just(response);
				}).switchIfEmpty(Mono.just(response)).flatMap(p -> {
					return ahorro.get().uri("/getCAhorroData/{doc}", params).accept(MediaType.APPLICATION_JSON_UTF8)
							.retrieve().bodyToFlux(CuentaAhorro.class).collectList().flatMap(q -> {
								SaldosDto respuesta;
								for (CuentaAhorro h : q) {
									respuesta = new SaldosDto();
									respuesta.setBanco(h.getBank());
								respuesta.setNumero(h.getNro_cuenta());
								respuesta.setProducto("Cuenta Ahorro");
								respuesta.setSaldo(h.getSaldo());
								response.add(respuesta);
								}
								return Mono.just(response);
							}).switchIfEmpty(Mono.just(response)).flatMap(m -> {
								return tc.get().uri("/getTCData/{doc}", params).accept(MediaType.APPLICATION_JSON_UTF8)
										.retrieve().bodyToFlux(CreditoTC.class).collectList().flatMap(o -> {
											SaldosDto respuesta;
											for (CreditoTC h : o) {
												respuesta = new SaldosDto();
												respuesta.setBanco(h.getBank());
												respuesta.setNumero(h.getNro_tarjeta());
												respuesta.setProducto("Tarjeta Credito");
												respuesta.setSaldo(h.getSaldo());
												response.add(respuesta);
											}

											return Mono.just(response);
										}).switchIfEmpty(Mono.just(response)).flatMap(d -> {
											return vip.get().uri("/getCcorrienteVipData/{doc}", params)
													.accept(MediaType.APPLICATION_JSON_UTF8).retrieve()
													.bodyToFlux(CuentaCorrienteVip.class).collectList().flatMap(n -> {
														SaldosDto respuesta;
														for (CuentaCorrienteVip h : n) {
															respuesta = new SaldosDto();
															respuesta.setBanco(h.getBank());
														respuesta.setNumero(h.getNro_cuenta());
														respuesta.setProducto("Cuenta Corriente Vip");
														respuesta.setSaldo(h.getSaldo());
														response.add(respuesta);
														}
														return Mono.just(response);
													});
										}).switchIfEmpty(Mono.just(response)).flatMap(d -> {
											return ecorriente.get().uri("/getCcorrienteEData/{doc}", params)
													.accept(MediaType.APPLICATION_JSON_UTF8).retrieve()
													.bodyToFlux(CuentaCorrienteE.class).collectList().flatMap(o -> {
														SaldosDto respuesta;
														for (CuentaCorrienteE h : o) {
															respuesta = new SaldosDto();
															respuesta.setBanco(h.getBank());
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

	@Override
	public Mono<List<ComisionesDto>> getRangeComisionesByNro_cuenta(String nro_cuenta, String from, String to) {
		// TODO Auto-generated method stub
		List<ComisionesDto> response = new ArrayList<ComisionesDto>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nro_cuenta", nro_cuenta);
		params.put("from", from);
		params.put("to", to);

		return vip.get().uri("/getRangeMovimientosCorrienteVip/{nro_cuenta}/{from}/{to}", params)
				.accept(MediaType.APPLICATION_JSON_UTF8).retrieve().bodyToFlux(Movimientos.class).collectList()
				.flatMap(o -> {
					ComisionesDto respuesta;
					for (Movimientos h : o) {
						if (h.getComision() > 0) {
							respuesta = new ComisionesDto();
							respuesta.setDescripcion(h.getDescripcion());
							respuesta.setFecha(h.getFecha());
							respuesta.setComision(h.getComision());
							response.add(respuesta);
						}
					}
					return Mono.just(response);
				}).switchIfEmpty(Mono.just(response)).flatMap(p -> {
					return ahorro.get().uri("/getRangeMovimientosAhorro/{nro_cuenta}/{from}/{to}", params)
							.accept(MediaType.APPLICATION_JSON_UTF8).retrieve().bodyToFlux(Movimientos.class)
							.collectList().flatMap(o -> {
								ComisionesDto respuesta;
								for (Movimientos h : o) {
									if (h.getComision() > 0) {
										respuesta = new ComisionesDto();
										respuesta.setDescripcion(h.getDescripcion());
										respuesta.setFecha(h.getFecha());
										respuesta.setComision(h.getComision());
										response.add(respuesta);
									}
								}
								return Mono.just(response);
							}).switchIfEmpty(Mono.just(response)).flatMap(f -> {
								return pcorriente.get()
										.uri("/getRangeMovimientosPcorriente/{nro_cuenta}/{from}/{to}", params)
										.accept(MediaType.APPLICATION_JSON_UTF8).retrieve()
										.bodyToFlux(Movimientos.class).collectList().flatMap(o -> {
											ComisionesDto respuesta;
											for (Movimientos h : o) {
												if (h.getComision() > 0) {
													respuesta = new ComisionesDto();
													respuesta.setDescripcion(h.getDescripcion());
													respuesta.setFecha(h.getFecha());
													respuesta.setComision(h.getComision());
													response.add(respuesta);
												}
											}
											return Mono.just(response);
										}).switchIfEmpty(Mono.just(response)).flatMap(k -> {
											return ecorriente.get()
													.uri("/getRangeMovimientosEcorriente/{nro_cuenta}/{from}/{to}",
															params)
													.accept(MediaType.APPLICATION_JSON_UTF8).retrieve()
													.bodyToFlux(Movimientos.class).collectList().flatMap(o -> {
														ComisionesDto respuesta;
														for (Movimientos h : o) {
															if (h.getComision() > 0) {
																respuesta = new ComisionesDto();
																respuesta.setDescripcion(h.getDescripcion());
																respuesta.setFecha(h.getFecha());
																respuesta.setComision(h.getComision());
																response.add(respuesta);
															}
														}
														return Mono.just(response);
													}).switchIfEmpty(Mono.just(response));
										});
							});
				});
	}

	@Override
	public Flux<Deudores> getDeudores() {
		// TODO Auto-generated method stub
		List<Deudores> response = new ArrayList<Deudores>();
		return tc.get().uri("/getDeudaVencida").accept(MediaType.APPLICATION_JSON_UTF8).retrieve()
				.bodyToFlux(CreditoTC.class).collectList().flatMapMany(o -> {

					Deudores respuesta;
					for (CreditoTC h : o) {
						respuesta = new Deudores();
						respuesta.setDocumento(h.getDoc());
						respuesta.setName(h.getName());
						respuesta.setLastname(h.getLastname());
						respuesta.setDeuda(h.getMinimo());
						response.add(respuesta);
					}
					
						return repo1.saveAll(response);
						
					
				}).next().flatMapMany(d -> {
					return tc.post().uri("/saveDeudoresTC").accept(MediaType.APPLICATION_JSON_UTF8)
							.body(BodyInserters.fromObject(response)).retrieve().bodyToFlux(Deudores.class)
							.flatMap(ptdc -> {
								return Flux.just(ptdc);
							}).next().flatMapMany(m -> {
								return pcorriente.post().uri("/saveDeudoresPcorriente")
										.accept(MediaType.APPLICATION_JSON_UTF8)
										.body(BodyInserters.fromObject(response)).retrieve().bodyToFlux(Deudores.class)
										.flatMap(ptdc -> {
											return Flux.just(ptdc);
										}).next().flatMapMany(j -> {
											return ahorro.post().uri("/saveDeudoresAhorro")
													.accept(MediaType.APPLICATION_JSON_UTF8)
													.body(BodyInserters.fromObject(response)).retrieve()
													.bodyToFlux(Deudores.class).flatMap(ptdc -> {
														return Flux.just(ptdc);
													}).next().flatMapMany(k -> {
														return vip.post().uri("/saveDeudoresVip")
																.accept(MediaType.APPLICATION_JSON_UTF8)
																.body(BodyInserters.fromObject(response)).retrieve()
																.bodyToFlux(Deudores.class).flatMap(ptdc -> {
																	return Flux.just(ptdc);
																});
													});

										});
							});
				});
	}

	@Override
	public Flux<Deudores> getDeudoresDoc(List<String> docs) {

		return repo1.findByDocumentoList(docs);

	}

}
