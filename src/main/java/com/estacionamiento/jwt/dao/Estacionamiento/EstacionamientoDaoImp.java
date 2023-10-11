package com.estacionamiento.jwt.dao.Estacionamiento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estacionamiento.jwt.model.Estacionamiento;
import com.estacionamiento.jwt.repository.EstacionamientoRepository;

import jakarta.transaction.Transactional;

@Repository
public class EstacionamientoDaoImp implements EstacionamientoDao{
	
	@Autowired
	EstacionamientoRepository estRepository;
	
	/*CRUD*/
	@Override
	public Estacionamiento CreateOrUpdateEstacionamiento(Estacionamiento estacionamiento) {
		return estRepository.save(estacionamiento);
	}
	
	@Override
	@Transactional
	public void deleteEstacionamientoByToken(String token) {
		estRepository.deleteByTokenIngreso(token);
	}
	
	@Override
	public Estacionamiento findEstacionamientoByTokenIngreso(String token) {
		return estRepository.findByTokenIngreso(token);
	}
	
	@Override
	public Long maxID() {
		return estRepository.maxCveEst();
	}
	
}
