package com.estacionamiento.jwt.service.usuario;

import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.model.Usuario;

@Service
public interface UsuarioService {

	Boolean findUsuarioByCorreoUsu(String corString);

	Usuario recoverUsuario(String correo, String pswd, String token);

	Usuario createNewUsu(String correo, String pswd, String token);

}
