package com.estacionamiento.jwt.dao.Historial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estacionamiento.jwt.model.Historial;
import com.estacionamiento.jwt.repository.HistorialRepository;

@Repository
public class HistorialDaoImp implements HistorialDao {
	
	@Autowired
	HistorialRepository repository;
	
	/*CRUD*/
	
	@Override
	public Historial saveOrUpdateHistorial(Historial hst) {
		return repository.save(hst);
	}
	
	@Override
	public Historial findHistorialById(Long id) {
		return repository.findHistoryByCveHist(id);
	}


}
