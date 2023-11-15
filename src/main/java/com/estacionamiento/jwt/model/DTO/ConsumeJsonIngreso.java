package com.estacionamiento.jwt.model.DTO;

public class ConsumeJsonIngreso {

	public long id;
	public boolean edopago;
	public long tipoUsuario;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isEdopago() {
		return edopago;
	}

	public void setEdopago(boolean edopago) {
		this.edopago = edopago;
	}

	public long getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(long tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}
