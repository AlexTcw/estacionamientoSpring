package com.estacionamiento.jwt.Dao.Estacionamiento;

import com.estacionamiento.jwt.model.Estacionamiento;

public interface EstacionamientoDao {


	void deleteEstacionamientoByToken(int token);

	Estacionamiento CreateOrUpdateEstacionamiento(Estacionamiento estacionamiento);

	Long maxID();

	Estacionamiento findEstacionamientoByTokenIngreso(int token);

	Long findLastId();

}
