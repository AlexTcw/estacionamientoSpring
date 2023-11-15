package com.estacionamiento.jwt.model.DTO;

import java.text.DecimalFormat;

public class ReciboDTO {

	String Correo;
	double totalHoras;
	double totalMinutos;
	double totalCosto;
	String mensaje;

	public String getCorreo() {
		return Correo;
	}

	public void setCorreo(String correo) {
		Correo = correo;
	}

	public double getTotalHoras() {
		return totalHoras;
	}

	public void setTotalHoras(double totalHoras) {
		this.totalHoras = totalHoras;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public double getTotalMinutos() {
		return totalMinutos;
	}

	public void setTotalMinutos(double totalMinutos) {
		this.totalMinutos = totalMinutos;
	}

	public double getTotalCosto() {
		return totalCosto;
	}

	public void setTotalCosto(double totalCosto) {
		DecimalFormat df = new DecimalFormat("#.##");
		this.totalCosto = Double.parseDouble(df.format(totalCosto));
	}

}
