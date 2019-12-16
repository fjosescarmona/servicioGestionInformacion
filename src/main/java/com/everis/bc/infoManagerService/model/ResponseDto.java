package com.everis.bc.infoManagerService.model;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ResponseDto {
	
	private List<CreditoTC> tc;
	
	private CuentaAhorro ca;
	
	private List<CuentaCorrienteE> cce;
	
	private CuentaCorrienteP ccp;

	public List<CreditoTC> getTc() {
		return tc;
	}

	public void setTc(List<CreditoTC> tc) {
		this.tc = tc;
	}

	public CuentaAhorro getCa() {
		return ca;
	}

	public void setCa(CuentaAhorro ca) {
		this.ca = ca;
	}

	public List<CuentaCorrienteE> getCce() {
		return cce;
	}

	public void setCce(List<CuentaCorrienteE> cce) {
		this.cce = cce;
	}

	public CuentaCorrienteP getCcp() {
		return ccp;
	}

	public void setCcp(CuentaCorrienteP ccp) {
		this.ccp = ccp;
	}
}
