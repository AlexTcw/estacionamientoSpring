package com.estacionamiento.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.estacionamiento.jwt.model.UsrPrincipal;

public interface UsrPrincipalRepository extends JpaRepository<UsrPrincipal, Long>{
	
    @Query(value = "select max(up.id_principal) from usr_principal up", nativeQuery = true)
    Long findMaxIdPrincipal();

    UsrPrincipal findByidPrincipal(long idPrincipal);

    UsrPrincipal findByTokenIngreso(String tokenIngreso);
}
