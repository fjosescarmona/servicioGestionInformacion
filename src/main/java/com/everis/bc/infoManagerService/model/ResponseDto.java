package com.everis.bc.infoManagerService.model;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ResponseDto {
	
	private List<CreditoTC> tc;
	
	private List<CuentaAhorro> ca;
	
	private List<CuentaCorrienteE> cce;
	
	private List<CuentaCorrienteP> ccp;
	
	private List<CuentaCorrienteVip> ccvip;

	public List<CuentaCorrienteVip> getCcvip() {
		return ccvip;
	}

	public void setCcvip(List<CuentaCorrienteVip> ccvip) {
		this.ccvip = ccvip;
	}

	public List<CreditoTC> getTc() {
		return tc;
	}

	public void setTc(List<CreditoTC> tc) {
		this.tc = tc;
	}

	public List<CuentaAhorro> getCa() {
		return ca;
	}

	public void setCa(List<CuentaAhorro> ca) {
		this.ca = ca;
	}

	public List<CuentaCorrienteE> getCce() {
		return cce;
	}

	public void setCce(List<CuentaCorrienteE> cce) {
		this.cce = cce;
	}

	public List<CuentaCorrienteP> getCcp() {
		return ccp;
	}

	public void setCcp(List<CuentaCorrienteP> ccp) {
		this.ccp = ccp;
	}
}
