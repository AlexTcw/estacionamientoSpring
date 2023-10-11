package com.estacionamiento.jwt.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.dao.Estacionamiento.EstacionamientoDao;
import com.estacionamiento.jwt.dao.usuario.UsuarioDao;
import com.estacionamiento.jwt.model.Estacionamiento;
import com.estacionamiento.jwt.model.Usuario;

@Service
public class UsuarioServiceImp implements UsuarioService {

	@Autowired
	UsuarioDao usudao;
	
	@Autowired
	EstacionamientoDao estDao;

	@Override
	public Boolean findUsuarioByCorreoUsu(String correo) {
		Usuario usuario = usudao.findUsuarioByCorreo(correo);

		if (usuario != null) {
			return true; // El usuario existe en la base de datos
		} else {
			return false; // El usuario no existe en la base de datos
		}
	}

	@Override
	public Usuario recoverUsuario(String correo, String pswd, String token) {

		if (correo == null || pswd == null) {
			return null;
		}

		Usuario usuario = usudao.findUsuarioByCorreo(correo);
		String password = usuario.getContraseña();
		if (password == pswd) {
			return usuario;
		} else {
			return null;
		}

	}

	@Override
	public Usuario createNewUsu(String correo, String pswd, String token) {

		if (correo == null || pswd == null) {
			return null;
		}

		Usuario usuario = new Usuario();
		usuario.setCorreo(correo);
		usuario.setContraseña(pswd);
		usuario.setTokenEst(token);

		usudao.createOrUpdateUsuario(usuario);

		return usuario;

	}

}
