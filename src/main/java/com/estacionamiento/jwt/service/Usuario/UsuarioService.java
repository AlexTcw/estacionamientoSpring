package com.estacionamiento.jwt.service.Usuario;

import java.util.List;

import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.model.Usuario;
import com.estacionamiento.jwt.model.DTO.UsrInfoDto;

@Service
public interface UsuarioService {

	Usuario createNewPensionUsuario(String correo, String pswd, int token, String placa, String nombre);

	int getUsuarioTokenByCorreoAndPSW(String correo, String contraseña);

	Boolean findUsuarioByCorreoUsu(String corString);

	Usuario recoverUsuario(String correo, String pswd, int token);

	Usuario createNewUsu(String correo, String pswd, int token, String placa, String nombre, Long edoUsu);

	Boolean existUsuario(String correo, String pass, int token);

	List<String> getTablaPLacas(int token);

	public UsrInfoDto getUsrInfo(int token);

	public void deleteUsuarioByCveUsu(int token);

	public void updateTableUsr(int token, String placa);

	public List<Usuario> getAllUsu();

	boolean updateTableUsr(String correo, String pswd, List<String> placas, String nombre, int token);

}
