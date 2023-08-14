package com.estacionamiento.jwt.model;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class UsrPrincipal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idPrincipal;

	private LocalDateTime ingreso;
	private LocalDateTime salida;
	private String tokenIngreso;
	private Double total;
	private String idPublica;

	public UsrPrincipal() {
		super();
	}

	public UsrPrincipal(Long idPrincipal, LocalDateTime ingreso, LocalDateTime salida, String tokenIngreso,
			Double total, String idPublica) {
		super();
		this.idPrincipal = idPrincipal;
		this.ingreso = ingreso;
		this.salida = salida;
		this.tokenIngreso = tokenIngreso;
		this.total = total;
		this.idPublica = idPublica;
	}
	

	public LocalDateTime getSalida() {
		return salida;
	}

	public void setSalida(LocalDateTime salida) {
		this.salida = salida;
	}

	public Long getIdPrincipal() {
		return idPrincipal;
	}

	public void setIdPrincipal(Long idPrincipal) {
		this.idPrincipal = idPrincipal;
	}

	public LocalDateTime getIngreso() {
		return ingreso;
	}

	public void setIngreso(LocalDateTime ingreso) {
		this.ingreso = ingreso;
	}

	public String getTokenIngreso() {
		return tokenIngreso;
	}

	public void setTokenIngreso(String tokenIngreso) {
		this.tokenIngreso = tokenIngreso;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getIdPublica() {
		return idPublica;
	}

	public void setIdPublica(String idPublica) {
		this.idPublica = idPublica;
	}

}
