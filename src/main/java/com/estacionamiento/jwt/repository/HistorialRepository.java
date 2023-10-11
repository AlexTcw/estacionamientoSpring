package com.estacionamiento.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estacionamiento.jwt.model.Historial;

public interface HistorialRepository extends JpaRepository<Historial, Long>{
	
	 Historial findHistoryByCveHist(Long id);
}
