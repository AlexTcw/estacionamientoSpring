package com.estacionamiento.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estacionamiento.jwt.model.UsrPrincipal;

public interface UsrPrincipalRepository extends JpaRepository<UsrPrincipal, Long>{

}
