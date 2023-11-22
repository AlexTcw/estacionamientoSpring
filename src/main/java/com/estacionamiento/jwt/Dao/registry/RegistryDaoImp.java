package com.estacionamiento.jwt.Dao.registry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estacionamiento.jwt.model.RegistrosHuellas;
import com.estacionamiento.jwt.repository.RegistryRepository;

@Repository
public class RegistryDaoImp implements RegistryDao {
	
	@Autowired
	RegistryRepository registryRepository;
	
	@Override
	public int getLastEdoRegistry() {
		return registryRepository.lastEdoRegistry();
	}
	
	@Override
	public int getLastTokenFromRegistry() {
		return registryRepository.lastToken();
	}
	
	@Override
	public RegistrosHuellas createNewEdo(RegistrosHuellas registro) {
		return registryRepository.save(registro);
	}

}
