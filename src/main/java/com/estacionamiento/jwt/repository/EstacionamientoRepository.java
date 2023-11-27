package com.estacionamiento.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.estacionamiento.jwt.model.Estacionamiento;

public interface EstacionamientoRepository extends JpaRepository<Estacionamiento, Long> {

	@Query(value = "SELECT cve_est FROM estacionamiento ORDER BY cve_est DESC LIMIT 1", nativeQuery = true)
	Long findLastId();

	Estacionamiento findEstacionamientoByTokenIngreso(int tokenIngreso);

	void deleteByTokenIngreso(int tokenIngreso);

	@Query(value = "select max(e.cve_est) from estacionamiento e ", nativeQuery = true)
	Long maxCveEst();

	@Query(value = "SELECT e.token_ingreso FROM estacionamiento e ORDER BY ingreso_fec DESC LIMIT 1", nativeQuery = true)
	Long findLatestEstacionamiento();

}
