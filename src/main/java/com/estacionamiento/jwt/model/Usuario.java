package com.estacionamiento.jwt.model;

import java.util.List;

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
	 */
	private Long edoUsu = 0L;
	private String correo = "generico@usuario";
	private String contraseña = "111111";
	private List<String> placas;
	private String nomUsu;

	private int tokenEst;

	public Usuario() {
		super();
	}

	public Usuario(Long cveUsu, Long edoUsu, String correo, String contraseña, List<String> placas, int tokenEst, String nomUsu) {
		this.cveUsu = cveUsu;
		this.edoUsu = edoUsu;
		this.correo = correo;
		this.contraseña = contraseña;
		this.placas = placas;
		this.tokenEst = tokenEst;
		this.nomUsu = nomUsu;
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

	public List<String> getPlacas() {
		return placas;
	}

	public void setPlacas(List<String> placas) {
		this.placas = placas;
	}

	public String getNomUsu() {
		return nomUsu;
	}

	public void setNomUsu(String nomUsu) {
		this.nomUsu = nomUsu;
	}

	


	

}
