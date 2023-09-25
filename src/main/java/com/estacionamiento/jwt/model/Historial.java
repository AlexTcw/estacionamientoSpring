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
	private Long id;

	private LocalDateTime ingreso;
	private LocalDateTime salida;
	private String idPublica;
	private Double total;
	private Double tiempoDeUso;

	public Historial() {
		super();
	}

	public Historial(Long id, LocalDateTime ingreso, LocalDateTime salida, String idPublica, Double total,
			Double tiempoDeUso) {
		super();
		this.id = id;
		this.ingreso = ingreso;
		this.salida = salida;
		this.idPublica = idPublica;
		this.total = total;
		this.tiempoDeUso = tiempoDeUso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getIngreso() {
		return ingreso;
	}

	public void setIngreso(LocalDateTime ingreso) {
		this.ingreso = ingreso;
	}

	public LocalDateTime getSalida() {
		return salida;
	}

	public void setSalida(LocalDateTime salida) {
		this.salida = salida;
	}

	public String getIdPublica() {
		return idPublica;
	}

	public void setIdPublica(String idPublica) {
		this.idPublica = idPublica;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getTiempoDeUso() {
		return tiempoDeUso;
	}

	public void setTiempoDeUso(Double tiempoDeUso) {
		this.tiempoDeUso = tiempoDeUso;
	}

}
