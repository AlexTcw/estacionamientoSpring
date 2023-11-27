package com.estacionamiento.jwt.Dao.Historial;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.estacionamiento.jwt.model.Historial;

@Repository
public interface HistorialDao {

	Historial saveOrUpdateHistorial(Historial hst);

	Historial findHistorialById(Long id);

	List<Historial> findAllHistorials();

}
