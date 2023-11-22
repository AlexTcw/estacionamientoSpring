package com.estacionamiento.jwt.Dao.registry;

import org.springframework.stereotype.Repository;

import com.estacionamiento.jwt.model.RegistrosHuellas;

@Repository
public interface RegistryDao {

	int getLastEdoRegistry();

	RegistrosHuellas createNewEdo(RegistrosHuellas registro);

	int getLastTokenFromRegistry();

}
