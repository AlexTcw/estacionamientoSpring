package com.estacionamiento.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.estacionamiento.jwt.model.RegistrosHuellas;

public interface RegistryRepository extends JpaRepository<RegistrosHuellas, Long>{
	
	//select max(r.id) from registros_huellas r	
	@Query(value = "SELECT r.edo_registro FROM registros_huellas r WHERE r.id = (SELECT MAX(id) FROM registros_huellas)", nativeQuery = true)
	int lastEdoRegistry();
	
	@Query(value = "SELECT r.token FROM registros_huellas r WHERE r.id = (SELECT MAX(id) FROM registros_huellas)", nativeQuery = true)
	int lastToken();
}
