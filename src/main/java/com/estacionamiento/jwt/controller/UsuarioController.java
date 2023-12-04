package com.estacionamiento.jwt.controller;

import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
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
			@RequestParam String placa, @RequestParam String nombre) {

		int token = registryService.getLastTokenRegistry();
		Boolean existPensionado = usuarioDao.existUsuarioByToken(token);
		Boolean existToken = estacionamientoService.existToken(token);

		if (existToken == false) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("No existe el token " + token + " debe haber un token para el usuario");
		}

		if (existPensionado == false) {
			Usuario usuario = usuarioService.createNewPensionUsuario(correo, pswd, token, placa, nombre);
			if (usuario != null) {
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

	// estado para validar al pensionado (no sirve para admin ya que el token de
	// admin no es unico)
	@GetMapping("/getEdoUsuForPension")
	public ResponseEntity<Object> getEdoUsuForPension(@RequestParam("token") int token) {
		Long edoUsu = usuarioService.getEdoUsuarioByTokenForPension(token);
		if (edoUsu == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("El usuario con token " + token + " no es pensionado o no existe");
		}
		if (edoUsu == 1L) {
			return ResponseEntity.ok(edoUsu);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no existe el token: " + token);

	}

	// For login pensionado solo si no tiene acceso a la huella o simplemente no
	// quiere
	@GetMapping("/loginUsuForPensionByCorreoAndPassword")
	public ResponseEntity<Object> validateUsuarioPension(
			@RequestParam("correo") String correo,
			@RequestParam("contraseña") String contraseña) {

		Boolean isPensionado = usuarioService.validateUsuarioPension(correo, contraseña);

		if (!isPensionado) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Correo o contraseña: " + correo + " no existe o es incorrecto para pensionado");
		}

		return ResponseEntity.ok(isPensionado);
	}

	// Pension Capabilities
	// recuperar las placas asociadas al token del Pensionado
	@GetMapping("/getUsrPensionTblPlacasByToken")
	public ResponseEntity<List<String>> getUsrPensionTblPlacasByToken() {
		int token = registryService.getLastTokenRegistry();
		Boolean existToken = usuarioDao.existUsuarioByToken(token);

		if (!existToken) {
			return ResponseEntity.badRequest().build();
		}

		List<String> tabla = usuarioService.getTablaPLacas(token);

		if (tabla != null && !tabla.isEmpty()) {
			return ResponseEntity.ok(tabla);
		}

		return ResponseEntity.noContent().build();
	}

	@PostMapping("/updateUsuTablePlacas")
	public void updateTableUsrPlacas(@RequestParam("placa") String placa) {
		int token = registryService.getLastTokenRegistry();
		usuarioService.updateTableUsr(token, placa);
	}

	/* Admin Path */
	// Primero creamos un usuario admin por consola
	@PostMapping("/SignUpAdmin")
	public ResponseEntity<String> createNewUsuarioAdmin(
			@RequestParam("correo") String correo,
			@RequestParam("contraseña") String contraseña,
			@RequestParam("nombre") String nombre) {

		Usuario usuario = usuarioService.createUsuarioAdmin(correo, contraseña, nombre);

		if (usuario != null) {
			return ResponseEntity.ok("Usuario " + usuario.getCorreo() + " creado");
		}

		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body("No se pudo crear el usuario Administrador con esas credenciales");
	}

	// Segundo valida si existe para acceder a su loby
	@GetMapping("/loginUsuForAdminByCorreoAndPassword")
	public ResponseEntity<Object> validateUsuarioAdmin(
			@RequestParam("correo") String correo,
			@RequestParam("contraseña") String contraseña) {
		Boolean isAdmin = usuarioService.existUsuarioAdministador(correo, contraseña);
		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Correo o contraseña: " + correo + " no existe o es incorrecto para pensionado");
		}

		return ResponseEntity.ok(isAdmin);
	}

	// Admin capabilities
	// borrar usuarios
	@GetMapping("/deleteUserByToken")
	public void deleteUserByCVE(@RequestParam int token) {
		usuarioService.deleteUsuarioByCveUsu(token);
	}
	// tambien puede recuperar, actualizar y borrar placas

	// Recupera todos los usuarios activos
	@GetMapping("/getAllUsu")
	public List<Usuario> getAllUsu() {
		return usuarioService.getAllUsu();
	}

	// borrar Usuarios
	@GetMapping("/deleteUsuById")
	public void deleteUserByCVE(@RequestParam Long cve) {
		usuarioDao.deleteUsuarioByCveUsu(cve);
	}

	// actualizar Usuarios
	@PostMapping("/updateUsu")
	public boolean updateTableUsr(@RequestParam String correo, @RequestParam String pswd,
			@RequestParam List<String> placas, @RequestParam String nombre, @RequestParam int token) {
		return usuarioService.updateTableUsr(correo, pswd, placas, nombre, token);
	}

	@GetMapping("/getTokenUsr")
	public int getUsuarioTokenByCorreo(@RequestParam String correo, @RequestParam String contraseña) {

		int tokUsu = usuarioService.getUsuarioTokenByCorreoAndPSW(correo, contraseña);

		return tokUsu;
	}

	@GetMapping("/descargar-csvEst")
	public ResponseEntity<ByteArrayResource> descargarCsv() {
		ByteArrayResource resource = usuarioService.createCsvEst();

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=estacionamientos.csv")
				.contentType(MediaType.parseMediaType("application/csv"))
				.contentLength(resource.contentLength())
				.body(resource);
	}
}
