package com.everis.bc.infoManagerService.model;

import java.util.Date;

public class ComisionesDto {

	private String descripcion;
	
	private Date fecha;
	
	private double comision;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getComision() {
		return comision;
	}

	public void setComision(double comision) {
		this.comision = comision;
	}
}
