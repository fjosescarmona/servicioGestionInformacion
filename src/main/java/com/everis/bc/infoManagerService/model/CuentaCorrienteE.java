package com.everis.bc.infoManagerService.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class CuentaCorrienteE {
	@Id
	private String id;
	@NotNull
	private String nro_cuenta;
	@NotNull
	private String tipo;
	@NotNull
	private double saldo;
	
	private List<Empresa> titulares;
	
	private List<Persona> firmantes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNro_cuenta() {
		return nro_cuenta;
	}

	public void setNro_cuenta(String nro_cuenta) {
		this.nro_cuenta = nro_cuenta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public List<Empresa> getTitulares() {
		return titulares;
	}

	public void setTitulares(List<Empresa> titulares) {
		this.titulares = titulares;
	}

	public List<Persona> getFirmantes() {
		return firmantes;
	}

	public void setFirmantes(List<Persona> firmantes) {
		this.firmantes = firmantes;
	}
	
	
}
