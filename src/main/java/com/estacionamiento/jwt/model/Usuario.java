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

	private String correo;
	private String contraseña;

	private String tokenEst;

	public Usuario() {
		super();
	}

	public Usuario(Long cveUsu, String correo, String contraseña, String tokenEst) {
		super();
		this.cveUsu = cveUsu;
		this.correo = correo;
		this.contraseña = contraseña;
		this.tokenEst = tokenEst;
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

	public String getTokenEst() {
		return tokenEst;
	}

	public void setTokenEst(String tokenEst) {
		this.tokenEst = tokenEst;
	}

}
