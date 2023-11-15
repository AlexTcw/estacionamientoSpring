package com.estacionamiento.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estacionamiento.jwt.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findUsuarioByCveUsu(Long cveUsu);
	
	Usuario findUsuarioBycorreo(String correo);
	
	Usuario findUsuarioByTokenEst(int token);

}
