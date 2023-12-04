package com.estacionamiento.jwt.service.Usuario;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.model.Estacionamiento;
import com.estacionamiento.jwt.model.Usuario;
import com.estacionamiento.jwt.model.DTO.UsrInfoDto;

@Service
public interface UsuarioService {

	Usuario createNewPensionUsuario(String correo, String pswd, int token, String placa, String nombre);

	int getUsuarioTokenByCorreoAndPSW(String correo, String contraseña);

	Boolean findUsuarioByCorreoUsu(String corString);

	Boolean existUsuario(String correo, String pass, int token);

	public Long getEdoUsuarioByTokenForPension(int token);

	public Boolean validateUsuarioPension(String correo, String contraseña);

	public Boolean existUsuarioAdministador(String correo, String contraseña);

	public Usuario createUsuarioAdmin(String correo, String contraseña, String nombre);

	List<String> getTablaPLacas(int token);

	public UsrInfoDto getUsrInfo(int token);

	public void deleteUsuarioByCveUsu(int token);

	public void updateTableUsr(int token, String placa);

	public List<Usuario> getAllUsu();

	boolean updateTableUsr(String correo, String pswd, List<String> placas, String nombre, int token);

	ByteArrayResource createCsvEst();

}
