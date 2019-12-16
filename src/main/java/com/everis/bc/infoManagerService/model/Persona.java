package com.everis.bc.infoManagerService.model;

import javax.validation.constraints.NotNull;

public class Persona {
	
	@NotNull
	private String name="";
	@NotNull
	private String lastname="";
	@NotNull
	private String doc="";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
}
