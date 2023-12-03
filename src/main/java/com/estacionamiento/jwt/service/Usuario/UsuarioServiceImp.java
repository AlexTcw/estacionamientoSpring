package com.estacionamiento.jwt.service.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.Dao.Estacionamiento.EstacionamientoDao;
import com.estacionamiento.jwt.Dao.Usuario.UsuarioDao;
import com.estacionamiento.jwt.model.Usuario;
import com.estacionamiento.jwt.model.DTO.UsrInfoDto;

@Service
public class UsuarioServiceImp implements UsuarioService {

	@Autowired
	UsuarioDao usudao;

	@Autowired
	EstacionamientoDao estDao;

	/*
	 * 0=generico
	 * 1=pensionado
	 * 2= admin
	 */

	// Pensionado Path
	// crear un nuevo Usuario Pensionado con token inmutable
	@Override
	public Usuario createNewPensionUsuario(String correo, String pswd, int token, String placa, String nombre) {
		Usuario usuario = new Usuario(null, 1L, correo, pswd, Collections.singletonList(placa), token, nombre);
		try {
			usudao.createOrUpdateUsuario(usuario);
		} catch (Exception e) {
			// Manejar la excepción apropiadamente
			e.printStackTrace(); // o loguear el error
			return null; // O manejar el error de alguna otra manera
		}

		return usuario;
	}

	// Recuperamos los datos importantes del usuario
	@Override
	public UsrInfoDto getUsrInfo(int token) {

		UsrInfoDto usrInfoDto = new UsrInfoDto();
		Usuario usuario = usudao.findUsuarioByToken(token);
		if (usuario != null) {
			List<String> placas = usuario.getPlacas();
			usrInfoDto.setNombreUsu(usuario.getNomUsu());
			usrInfoDto.setCorreoUsu(usuario.getCorreo());
			usrInfoDto.setPlacas(placas);
			usrInfoDto.setToken(token);

			return usrInfoDto;

		}
		return null;
	}

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
	public int getUsuarioTokenByCorreoAndPSW(String correo, String contraseña) {
		Usuario usuario = usudao.findUsuarioByCorreo(correo);

		if (usuario != null) {
			// Utiliza el método equals para comparar cadenas
			if (usuario.getContraseña().equals(contraseña)) {
				return usuario.getTokenEst();
			}
			return 0;
		}

		return 0;
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
	public Usuario createNewUsu(String correo, String pswd, int token, String placa, String nombre, Long edoUsu) {
		if (correo == null || pswd == null) {
			return null;
		}

		List<String> placas = new ArrayList<>();
		placas.add(placa); // Agregar el valor de la placa en lugar de la dirección de correo

		Usuario usuario = new Usuario();
		usuario.setCorreo(correo);
		usuario.setContraseña(pswd);
		usuario.setTokenEst(token);
		usuario.setEdoUsu(edoUsu);
		usuario.setPlacas(placas);
		usuario.setNomUsu(nombre);

		usudao.createOrUpdateUsuario(usuario);

		return usuario;
	}

	@Override
	public List<String> getTablaPLacas(int token) {

		Usuario usr = usudao.findUsuarioByToken(token);
		if (usr != null) {
			List<String> placas = usr.getPlacas();
			return placas;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public void deleteUsuarioByCveUsu(int token) {

		List<Usuario> usuariosToDelete = usudao.findUsuariosByToken(token);

		for (Usuario usuario : usuariosToDelete) {
			if (usuario.getEdoUsu() == 0L) {
				usudao.deleteUsuarioByCveUsu(usuario.getCveUsu());
			}
		}
	}

	@Override
	public void updateTableUsr(int token, String placa) {
		Usuario usuario = usudao.findUsuarioByToken(token);
		if (usuario != null && !usuario.getPlacas().contains(placa)) {
			usuario.getPlacas().add(placa);
			usudao.createOrUpdateUsuario(usuario);
		}
	}

	@Override
	public List<Usuario> getAllUsu() {
		return usudao.getAllUsu();
	}

	@Override
	public boolean updateTableUsr(String correo, String pswd, List<String> placas, String nombre, int token) {
		Usuario usuario = usudao.findUsuarioByToken(token);
		usuario.setContraseña(pswd);
		usuario.setCorreo(correo);
		usuario.setPlacas(placas);
		usuario.setNomUsu(nombre);

		usudao.createOrUpdateUsuario(usuario);

		return true;
	}
}
