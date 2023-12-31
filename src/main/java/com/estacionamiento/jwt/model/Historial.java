package com.estacionamiento.jwt.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Historial {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cveHist;

	private Double tiempoDeUso;
	private LocalDateTime ingresoFec;
	private LocalDateTime salidaFec;
	private Double total;
	private Long cveEst;

	public Historial() {
		super();
	}

	public Historial(Long cveHist, Double tiempoDeUso, LocalDateTime ingresoFec, LocalDateTime salidaFec, Double total,
			Long cveEst) {
		super();
		this.cveHist = cveHist;
		this.tiempoDeUso = tiempoDeUso;
		this.ingresoFec = ingresoFec;
		this.salidaFec = salidaFec;
		this.total = total;
		this.cveEst = cveEst;
	}

	public Long getCveHist() {
		return cveHist;
	}

	public void setCveHist(Long cveHist) {
		this.cveHist = cveHist;
	}

	public Double getTiempoDeUso() {
		return tiempoDeUso;
	}

	public void setTiempoDeUso(Double tiempoDeUso) {
		this.tiempoDeUso = tiempoDeUso;
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

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Long getCveEst() {
		return cveEst;
	}

	public void setCveEst(Long cveEst) {
		this.cveEst = cveEst;
	}

}
