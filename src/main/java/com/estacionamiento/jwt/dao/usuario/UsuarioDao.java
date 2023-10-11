package com.estacionamiento.jwt.dao.usuario;

import com.estacionamiento.jwt.model.Usuario;

public interface UsuarioDao {

	Usuario createOrUpdateUsuario(Usuario usr);

	void deleteUsuarioByCveUsu(Long cve);

	Usuario findUsuarioByCorreo(String correo);

	Usuario findUsuarioByToken(String token);

}
