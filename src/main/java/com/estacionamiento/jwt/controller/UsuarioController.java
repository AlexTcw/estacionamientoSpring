package com.estacionamiento.jwt.controller;

import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamiento.jwt.Dao.Usuario.UsuarioDao;
import com.estacionamiento.jwt.model.Usuario;
import com.estacionamiento.jwt.model.DTO.UsrInfoDto;
import com.estacionamiento.jwt.service.Estacionamiento.EstacionamientoService;
import com.estacionamiento.jwt.service.Usuario.UsuarioService;
import com.estacionamiento.jwt.service.registry.RegistryService;

import jakarta.persistence.NonUniqueResultException;

@RestController
@CrossOrigin(origins = { "*" })
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	UsuarioDao usuarioDao;

	@Autowired
	RegistryService registryService;

	@Autowired
	EstacionamientoService estacionamientoService;

	@PostMapping("/createUsuPension")
	public ResponseEntity<String> createNewPensionUserWithToken(@RequestParam String correo, @RequestParam String pswd,
			@RequestParam int token, @RequestParam String placa, @RequestParam String nombre) {

		Boolean existPensionado = usuarioDao.existUsuarioByToken(token);
		Boolean existToken = estacionamientoService.existToken(token);

		if (existToken == false) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("No existe el token " + token + " debe haber un token para el usuario");
		}

		if (existPensionado == false) {
			Usuario usuario = usuarioService.createNewPensionUsuario(correo, pswd, token, placa, nombre);
			if (usuario != null) {
				registryService.setRegistry2proccess();
				return ResponseEntity.ok("Usuario creado para el token: " + token);
			}
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("No se pudo crear el usuario con el token: " + token);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un Pensionado con token: " + token);
	}

	@PostMapping("/getPensionadoInfoByToken")
	public ResponseEntity<UsrInfoDto> getUsrPensionadoInfoByToken(@RequestParam("token") int token) {
		UsrInfoDto usr = usuarioService.getUsrInfo(token);
		if (usr != null) {
			return ResponseEntity.ok(usr);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(usr);
	}

	@GetMapping("/existUsu")
	public ResponseEntity<String> recoverUsu(@RequestParam String correo, @RequestParam String pswd,
			@RequestParam int token) {
		Boolean usuarioExist = usuarioService.existUsuario(correo, pswd, token);
		if (usuarioExist) {
			return ResponseEntity.ok("usuario encontrado");
		} else {
			return ResponseEntity.badRequest().body("La contraseña no es válida.");
		}
	}

	@PostMapping("/createUsuario")
	public ResponseEntity<String> createUsu(@RequestParam String correo, @RequestParam String pswd,
			@RequestParam int token, @RequestParam String placa, @RequestParam String nombre) {
		try {
			if (correo.equals("generico@usuario")) {
				return ResponseEntity.badRequest().body("Usuario no válido");
			}
			Boolean findUsr = usuarioService.findUsuarioByCorreoUsu(correo);
			if (findUsr == false) {
				Long edoUsu = 1L;
				usuarioService.createNewUsu(correo, pswd, token, placa, nombre, edoUsu);

				List<Usuario> usuariosToDelete = usuarioDao.findUsuariosByToken(token);

				for (Usuario usuario : usuariosToDelete) {
					if (usuario.getEdoUsu() == 0L) {
						usuarioDao.deleteUsuarioByCveUsu(usuario.getCveUsu());
					}
				}

				return ResponseEntity.ok("usuario creado, redirigiendo");
			}
			return ResponseEntity.badRequest().body("Usuario no válido");
		} catch (NonUniqueResultException e) {
			return ResponseEntity.badRequest().body("Usuario no válido");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Usuario no válido");

		}
	}

	@PostMapping("/createUsuarioAdmin")
	public ResponseEntity<String> createUsuAdmin(@RequestParam String correo, @RequestParam String pswd,
			@RequestParam int token, @RequestParam String nombre) {
		try {
			if (correo.equals("generico@usuario")) {
				return ResponseEntity.badRequest().body("Usuario no válido");
			}
			Boolean findUsr = usuarioService.findUsuarioByCorreoUsu(correo);
			if (findUsr == false) {
				Long edoUsu = 2L;
				String placa = "";
				usuarioService.createNewUsu(correo, pswd, token, placa, nombre, edoUsu);

				List<Usuario> usuariosToDelete = usuarioDao.findUsuariosByToken(token);

				for (Usuario usuario : usuariosToDelete) {
					if (usuario.getEdoUsu() == 0L) {
						usuarioDao.deleteUsuarioByCveUsu(usuario.getCveUsu());
					}
				}

				return ResponseEntity.ok("usuario creado");
			}
			return ResponseEntity.badRequest().body("Usuario no válido");
		} catch (NonUniqueResultException e) {
			return ResponseEntity.badRequest().body("Usuario no válido");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Usuario no válido");

		}
	}

	@GetMapping("/getUsrTabl")
	public List<String> getTable(@RequestParam int token) {
		List<String> tabla = usuarioService.getTablaPLacas(token);
		return tabla;
	}

	@GetMapping("/getUsrInfo")
	public UsrInfoDto getInfoUsr(@RequestParam int token) {
		return usuarioService.getUsrInfo(token);
	}

	@GetMapping("/deleteUserByCVE")
	public void deleteUserByCVE(@RequestParam int token) {
		usuarioService.deleteUsuarioByCveUsu(token);
	}

	@PostMapping("/updateUsuTable")
	public void updateTableUsr(@RequestParam int token, @RequestParam String placa) {
		usuarioService.updateTableUsr(token, placa);
	}

	@GetMapping("/getTokenUsr")
	public int getUsuarioTokenByCorreo(@RequestParam String correo, @RequestParam String contraseña) {

		int tokUsu = usuarioService.getUsuarioTokenByCorreoAndPSW(correo, contraseña);

		return tokUsu;
	}

	@GetMapping("/getAllUsu")
	public List<Usuario> getAllUsu() {
		return usuarioService.getAllUsu();
	}

	@GetMapping("/deleteUsuById")
	public void deleteUserByCVE(@RequestParam Long cve) {
		usuarioDao.deleteUsuarioByCveUsu(cve);
	}

	@PostMapping("updateUsu")
	public boolean updateTableUsr(@RequestParam String correo, @RequestParam String pswd,
			@RequestParam List<String> placas, @RequestParam String nombre, @RequestParam int token) {
		return usuarioService.updateTableUsr(correo, pswd, placas, nombre, token);
	}

}
