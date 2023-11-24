package com.estacionamiento.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamiento.jwt.Dao.Usuario.UsuarioDao;
import com.estacionamiento.jwt.model.Usuario;
import com.estacionamiento.jwt.service.Usuario.UsuarioService;

import jakarta.persistence.NonUniqueResultException;

@RestController
@CrossOrigin(origins = { "*" })
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	UsuarioDao usuarioDao;

	@GetMapping("/recoverOrCreateUSU")
	public ResponseEntity<?> recoverOrCreateUsuario(@RequestParam String correo, @RequestParam String pswd,
			@RequestParam(value = "token", required = false) int token) {
		if (correo == null || pswd == null) {
			return ResponseEntity.badRequest().body("Debes ingresar tu correo y contraseña.");
		}

		Boolean usuarioExist = usuarioService.findUsuarioByCorreoUsu(correo);

		if (usuarioExist) {
			Usuario usuario = usuarioService.recoverUsuario(correo, pswd, token);
			if (usuario != null) {
				return ResponseEntity.ok(usuario);
			} else {
				return ResponseEntity.badRequest().body("La contraseña no es válida.");
			}
		} else {
			Usuario nuevoUsuario = usuarioService.createNewUsu(correo, pswd, token);
			if (nuevoUsuario != null) {
				return ResponseEntity.ok(nuevoUsuario);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo crear el usuario.");
			}
		}
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
			@RequestParam int token) {
		try {
			if (correo.equals("generico@usuario")) {
				return ResponseEntity.badRequest().body("Usuario no válido");
			}
			Boolean findUsr = usuarioService.findUsuarioByCorreoUsu(correo);
			if (findUsr == false) {
				Usuario Usr = usuarioService.createNewUsu(correo, pswd, token);
				return ResponseEntity.ok("usuario creado: "+Usr);
			}
			return ResponseEntity.badRequest().body("Usuario no válido");
		} catch (NonUniqueResultException e) {
			return ResponseEntity.badRequest().body("Usuario no válido");
		} catch (Exception e) {			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Usuario no válido");
		
		}
	}

}
