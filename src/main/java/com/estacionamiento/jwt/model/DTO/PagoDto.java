package com.estacionamiento.jwt.model.DTO;

import java.time.LocalDateTime;

public class PagoDto {

	Double subTotal;
	Double total;
	String fechaEntradaString;
	String fechaSalida;
	String fecha;
	LocalDateTime fechaCompleta;

	public LocalDateTime getFechaCompleta() {
		return fechaCompleta;
	}

	public void setFechaCompleta(LocalDateTime fechaCompleta) {
		this.fechaCompleta = fechaCompleta;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

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

	public String getFechaEntradaString() {
		return fechaEntradaString;
	}

	public void setFechaEntradaString(String fechaEntradaString) {
		this.fechaEntradaString = fechaEntradaString;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

}
