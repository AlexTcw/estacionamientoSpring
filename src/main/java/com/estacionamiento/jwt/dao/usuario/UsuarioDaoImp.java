package com.estacionamiento.jwt.dao.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estacionamiento.jwt.model.Usuario;
import com.estacionamiento.jwt.repository.UsuarioRepository;

@Repository
public class UsuarioDaoImp implements UsuarioDao {

	@Autowired
	UsuarioRepository usrRepository;

	/* CRUD */
	@Override
	public Usuario createOrUpdateUsuario(Usuario usr) {
		return usrRepository.save(usr);
	}
	
	@Override
	public Usuario findUsuarioByCorreo(String correo) {
		return usrRepository.findUsuarioBycorreo(correo);
	}
	
	@Override
	public void deleteUsuarioByCveUsu(Long cve) {
		usrRepository.deleteById(cve);
	}
	
	@Override
	public Usuario findUsuarioByToken(String token) {
		return usrRepository.findUsuarioByTokenEst(token);
	}
}
