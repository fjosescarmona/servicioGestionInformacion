package com.everis.bc.infoManagerService.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="c_products")
public class ProductDetails {

	@Id
	private String id;
	@NotNull
	private String doc;
	@NotNull
	private CuentaCorrienteP ccp;
	
	private List<CuentaCorrienteE> cce;
	@NotNull
	private List<CreditoTC> tc;
	@NotNull
	private CuentaAhorro ca;
	
	
	public List<CreditoTC> getTc() {
		return tc;
	}
	public void setTc(List<CreditoTC> tc) {
		this.tc = tc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	public CuentaCorrienteP getCcp() {
		return ccp;
	}
	public void setCcp(CuentaCorrienteP ccp) {
		this.ccp = ccp;
	}
	public List<CuentaCorrienteE> getCce() {
		return cce;
	}
	public void setCce(List<CuentaCorrienteE> cce) {
		this.cce = cce;
	}
	public CuentaAhorro getCa() {
		return ca;
	}
	public void setCa(CuentaAhorro ca) {
		this.ca = ca;
	}
}
