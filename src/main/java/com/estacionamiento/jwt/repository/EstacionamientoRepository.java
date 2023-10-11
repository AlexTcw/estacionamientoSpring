package com.estacionamiento.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.estacionamiento.jwt.model.Estacionamiento;

public interface EstacionamientoRepository extends JpaRepository<Estacionamiento, Long> {

	Estacionamiento findByTokenIngreso(String tokenIngreso);

	void deleteByTokenIngreso(String tokenIngreso);

	@Query(value = "select max(e.cve_est) from estacionamiento e ", nativeQuery = true)
	Long maxCveEst();
}
