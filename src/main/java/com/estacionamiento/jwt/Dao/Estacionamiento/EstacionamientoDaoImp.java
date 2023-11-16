package com.estacionamiento.jwt.Dao.Estacionamiento;

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
	public void deleteEstacionamientoByToken(int token) {
		estRepository.deleteByTokenIngreso(token);
	}
	
	@Override
	public Estacionamiento findEstacionamientoByTokenIngreso(int token) {
		return estRepository.findByTokenIngreso(token);
	}
	
	@Override
	public Long maxID() {
		return estRepository.maxCveEst();
	}

	@Override
	public Long findLastId() {
		// TODO Auto-generated method stub
		return estRepository.findLastId();
	}
	
	@Override
	public Long findLastToken() {
		return estRepository.findLatestEstacionamiento();
	}
	
}
