package com.estacionamiento.jwt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RegistrosHuellas {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;
	public int edoRegistro;
	public int token;
	/*
	 * 1.Ingreso 2 En proceso. 3 Salida.
	 */

	public RegistrosHuellas() {
		super();
	}

	public RegistrosHuellas(long id, int edoRegistro, int token) {
		super();
		this.id = id;
		this.edoRegistro = edoRegistro;
		this.token = token;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getEdoRegistro() {
		return edoRegistro;
	}

	public void setEdoRegistro(int edoRegistro) {
		this.edoRegistro = edoRegistro;
	}

}
