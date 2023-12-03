package com.estacionamiento.jwt.model.DTO;

import java.time.LocalDateTime;

public class PagoInfoDto {

	Double subTotal;
	Double total;
	String fechaEntrada;
	String fechaSalida;
	String fecha;
	LocalDateTime fechaCompleta;

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(String fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public LocalDateTime getFechaCompleta() {
		return fechaCompleta;
	}

	public void setFechaCompleta(LocalDateTime fechaCompleta) {
		this.fechaCompleta = fechaCompleta;
	}

}
