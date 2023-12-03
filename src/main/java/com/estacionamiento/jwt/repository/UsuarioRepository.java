package com.estacionamiento.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estacionamiento.jwt.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	boolean existsUsuarioByTokenEst(int token);

	Usuario findUsuarioByCveUsu(Long cveUsu);

	Usuario findUsuarioBycorreo(String correo);

	Usuario findUsuarioByTokenEst(int token);

	List<Usuario> findUsuariosByTokenEst(int token);

	Usuario findByCorreoAndContraseñaAndTokenEst(String correo, String contrasena, int token);

	boolean existsByCorreoAndContraseñaAndTokenEst(String correo, String contrasena, int token);

	void deleteUsuarioByTokenEstAndEdoUsu(int token, Long edousu);

}
