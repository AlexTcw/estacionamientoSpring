package com.estacionamiento.jwt.dao.Historial;

import org.springframework.stereotype.Repository;

import com.estacionamiento.jwt.model.Historial;

@Repository
public interface HistorialDao {

	Historial saveOrUpdateHistorial(Historial hst);

	Historial findHistorialById(Long id);

}
