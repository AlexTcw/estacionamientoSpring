package com.estacionamiento.jwt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cveUsu;
	
	/*
	 * 0=generico
	 * 1=pensionado
	 * 2= admin
	 * */
	private Long edoUsu = 0L;
	private String correo;
	private String contraseña;

	private int tokenEst;

	public Usuario() {
		super();
	}

	public Usuario(Long cveUsu, Long edoUsu, String correo, String contraseña, int tokenEst) {
		super();
		this.cveUsu = cveUsu;
		this.edoUsu = edoUsu;
		this.correo = correo;
		this.contraseña = contraseña;
		this.tokenEst = tokenEst;
	}

	public Long getEdoUsu() {
		return edoUsu;
	}

	public void setEdoUsu(Long edoUsu) {
		this.edoUsu = edoUsu;
	}

	public Long getCveUsu() {
		return cveUsu;
	}

	public void setCveUsu(Long cveUsu) {
		this.cveUsu = cveUsu;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public int getTokenEst() {
		return tokenEst;
	}

	public void setTokenEst(int tokenEst) {
		this.tokenEst = tokenEst;
	}

}
