package com.estacionamiento.jwt.service.Usuario;

import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.model.Usuario;

@Service
public interface UsuarioService {

	Boolean findUsuarioByCorreoUsu(String corString);

	Usuario recoverUsuario(String correo, String pswd, int token);

	Usuario createNewUsu(String correo, String pswd, int token);

	Boolean existUsuario(String correo, String pass, int token);

}
