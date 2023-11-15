package com.estacionamiento.jwt.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Estacionamiento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cveEst;

	private LocalDateTime ingresoFec;
	private LocalDateTime salidaFec;
	private int tokenIngreso;
	private Double total;

	public Estacionamiento() {
		super();
	}

	public Estacionamiento(Long cveEst, LocalDateTime ingresoFec, LocalDateTime salidaFec, int tokenIngreso,
			Double total) {
		super();
		this.cveEst = cveEst;
		this.ingresoFec = ingresoFec;
		this.salidaFec = salidaFec;
		this.tokenIngreso = tokenIngreso;
		this.total = total;
	}

	public Long getCveEst() {
		return cveEst;
	}

	public void setCveEst(Long cveEst) {
		this.cveEst = cveEst;
	}

	public LocalDateTime getIngresoFec() {
		return ingresoFec;
	}

	public void setIngresoFec(LocalDateTime ingresoFec) {
		this.ingresoFec = ingresoFec;
	}

	public LocalDateTime getSalidaFec() {
		return salidaFec;
	}

	public void setSalidaFec(LocalDateTime salidaFec) {
		this.salidaFec = salidaFec;
	}

	public int getTokenIngreso() {
		return tokenIngreso;
	}

	public void setTokenIngreso(int tokenIngreso) {
		this.tokenIngreso = tokenIngreso;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
