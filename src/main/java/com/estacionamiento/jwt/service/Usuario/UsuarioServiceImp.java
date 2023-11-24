package com.estacionamiento.jwt.service.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.Dao.Estacionamiento.EstacionamientoDao;
import com.estacionamiento.jwt.Dao.Usuario.UsuarioDao;
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
	public Usuario recoverUsuario(String correo, String pswd, int token) {

		if (correo == null || pswd == null) {
			return null;
		}
		return usudao.finUsuarioByCorreoYpass(correo, pswd, token);		

	}
	
	@Override 
	public Boolean existUsuario(String correo, String pass, int token) {
		return usudao.existUsuarioByCorreoAndContraseñaAndTokenEst(correo, pass, token);
	} 

	@Override
	public Usuario createNewUsu(String correo, String pswd, int token) {

		if (correo == null || pswd == null) {
			return null;
		}

		Usuario usuario = new Usuario();
		usuario.setCorreo(correo);
		usuario.setContraseña(pswd);
		usuario.setTokenEst(token);
		usuario.setEdoUsu(1L);

		usudao.createOrUpdateUsuario(usuario);

		return usuario;
	}		
}
