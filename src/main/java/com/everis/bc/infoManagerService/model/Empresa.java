package com.everis.bc.infoManagerService.model;

import javax.validation.constraints.NotNull;

public class Empresa {
	
	@NotNull
	private String r_social="";
	@NotNull
	private String doc="";
	@NotNull
	private String producto="";
	
	public String getR_social() {
		return r_social;
	}
	public void setR_social(String r_social) {
		this.r_social = r_social;
	}
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}

}
