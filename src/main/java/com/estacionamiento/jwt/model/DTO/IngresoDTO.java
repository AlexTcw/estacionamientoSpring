package com.estacionamiento.jwt.model.DTO;

import java.time.LocalDateTime;

public class IngresoDTO {

	LocalDateTime ingreso;
	String mensaje;


	public LocalDateTime getIngreso() {
		return ingreso;
	}

	public void setIngreso(LocalDateTime ingreso) {
		this.ingreso = ingreso;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
