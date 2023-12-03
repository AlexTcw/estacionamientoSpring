package com.estacionamiento.jwt.Dao.Usuario;

import java.util.List;

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
	public Usuario findUsuarioByToken(int token) {
		return usrRepository.findUsuarioByTokenEst(token);
	}

	@Override
	public Usuario finUsuarioByCorreoYpass(String correo, String pass, int token) {
		return usrRepository.findByCorreoAndContraseñaAndTokenEst(correo, correo, token);
	}

	@Override
	public Boolean existUsuarioByCorreoAndContraseñaAndTokenEst(String correo, String pass, int token) {
		return usrRepository.existsByCorreoAndContraseñaAndTokenEst(correo, pass, token);
	}

	@Override
	public void deleteUsuarioByTokenEstAndEdoUsu(int token, Long edoUsu) {
		usrRepository.deleteUsuarioByTokenEstAndEdoUsu(token, edoUsu);
	}

	@Override
	public List<Usuario> findUsuariosByToken(int token) {
		return usrRepository.findUsuariosByTokenEst(token);
	}

	@Override
	public List<Usuario> getAllUsu() {
		return usrRepository.findAll();
	}

	@Override
	public Boolean existUsuarioByToken(int token) {
		System.out.println(usrRepository.existsUsuarioByTokenEst(token));
		return usrRepository.existsUsuarioByTokenEst(token);
	}
}
