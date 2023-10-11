package com.estacionamiento.jwt.dao.Estacionamiento;

import com.estacionamiento.jwt.model.Estacionamiento;

public interface EstacionamientoDao {


	void deleteEstacionamientoByToken(String token);

	Estacionamiento findEstacionamientoByTokenIngreso(String token);

	Estacionamiento CreateOrUpdateEstacionamiento(Estacionamiento estacionamiento);

	Long maxID();

}
