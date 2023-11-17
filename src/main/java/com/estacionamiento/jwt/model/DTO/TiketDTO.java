package com.estacionamiento.jwt.model.DTO;

public class TiketDTO {
	
	String LogoTXT = "MI EMPRESA";
	String fecha;
	String horaEntrada;
	String horaSalida;
	String total;
	public String getLogoTXT() {
		return LogoTXT;
	}
	public void setLogoTXT(String logoTXT) {
		LogoTXT = logoTXT;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHoraEntrada() {
		return horaEntrada;
	}
	public void setHoraEntrada(String horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	public String getHoraSalida() {
		return horaSalida;
	}
	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
	

}
