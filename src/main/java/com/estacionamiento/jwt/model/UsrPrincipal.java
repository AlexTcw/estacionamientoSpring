package com.estacionamiento.jwt.model;

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

	@Temporal(TemporalType.DATE)
	private Date ingreso;
	@Temporal(TemporalType.DATE)
	private Date salida;
	private String tokenIngreso;
	private Double total;

	public UsrPrincipal() {
		super();
	}

	public UsrPrincipal(Long idPrincipal, Date ingreso, Date salida, String tokenIngreso, Double total) {
		super();
		this.idPrincipal = idPrincipal;
		this.ingreso = ingreso;
		this.salida = salida;
		this.tokenIngreso = tokenIngreso;
		this.total = total;
	}

	public Long getIdPrincipal() {
		return idPrincipal;
	}

	public void setIdPrincipal(Long idPrincipal) {
		this.idPrincipal = idPrincipal;
	}

	public Date getIngreso() {
		return ingreso;
	}

	public void setIngreso(Date ingreso) {
		this.ingreso = ingreso;
	}

	public Date getSalida() {
		return salida;
	}

	public void setSalida(Date salida) {
		this.salida = salida;
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

}
