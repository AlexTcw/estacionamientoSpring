package com.estacionamiento.jwt.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamiento.jwt.Dao.Estacionamiento.EstacionamientoDao;
import com.estacionamiento.jwt.model.Estacionamiento;
import com.estacionamiento.jwt.model.DTO.BienvenidaDTO;
import com.estacionamiento.jwt.model.DTO.PagoInfoDto;
import com.estacionamiento.jwt.service.Estacionamiento.EstacionamientoService;
import com.estacionamiento.jwt.service.registry.RegistryService;

@RestController
@CrossOrigin(origins = { "*" })
public class EstacionamientoController {

	@Autowired
	RegistryService registryService;

	@Autowired
	EstacionamientoService estacionamientoService;

	@Autowired
	EstacionamientoDao estacionamientoDao;

	// fingerPrintFuction
	@GetMapping("/generateToken")
	public int generateTokenFromFP() {
		return estacionamientoService.generateNewToken();
	}

	/*
	 * Generic Usr Path
	 * valida si existe un estacionamiento registrado con ese token
	 * si lo hay ejecuta la logica de salida
	 * sino crea un nuevo estacionamiento
	 * sino puede ninguno regresa un badRequest
	 */
	@PostMapping("/entryOrExit")
	public ResponseEntity<String> handleEntryOrExit(@RequestParam("token") int token) {
		// Verificar si el token existe en el sistema
		boolean tokenExists = estacionamientoService.existToken(token);
		if (!tokenExists) {
			// Entrada: Crear nuevo registro de usuario en el estacionamiento
			estacionamientoService.createNewEstacionamientoWithToken(token);
			registryService.setRegistry1Enrroll(token);

			return ResponseEntity.ok("Nuevo registro de estacionamiento para el token: " + token);
		} else {
			// Salida: Actualizar registro de salida para el usuario
			registryService.setRegistry3Exit(token);
			return ResponseEntity.ok("Usuario saliendo con token: " + token);
		}
	}

	// recuperamos la fecha/hora de entrada para el front
	@GetMapping("/getFechaEstByToken")
	public ResponseEntity<PagoInfoDto> getFechaEstFront(@RequestParam("token") int token) {
		boolean tokenExists = estacionamientoService.existToken(token);
		PagoInfoDto pagoInfoDto = estacionamientoService.getPagoInfo(token);
		if (tokenExists || pagoInfoDto != null) {
			return ResponseEntity.ok(pagoInfoDto);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(pagoInfoDto);
		}
	}

	// establece los datos de salida en la tabla Estacionamiento
	@PostMapping("/setExitData")
	public ResponseEntity<String> setExitData(@RequestParam("token") int token) {
		boolean tokenExists = estacionamientoService.existToken(token);
		if (tokenExists) {
			boolean estacionamiento = estacionamientoService.setExitDataEstacionamientoWithToken(token);
			if (!estacionamiento) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body("No se pudo actualizar estacionamiento con token: " + token);
			}
			registryService.setRegistry3Exit(token);
			return ResponseEntity.ok("Estacionamiento con token: " + token + " actualizado");
		} else
			registryService.setRegistry2proccess();
		return ResponseEntity.ok("No existe el estacionamiento con token: " + token);
	}

	// Borra al usuario una vez ha pagado
	@PostMapping("/deleteEstacionamientoByToken")
	public ResponseEntity<String> deleteEstacionamientoByToken(@RequestParam("token") int token) {
		Estacionamiento estacionamiento = estacionamientoService.getEstacionamientoByToken(token);
		if (estacionamiento != null) {
			estacionamientoService.deleteEstacionamientoByToken(token);
			registryService.setRegistry2proccess();
			return ResponseEntity.ok("Estacionamiento con token: " + token + " borrado");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el estacionamiento con token: " + token);
	}

	// Pension path
	@PostMapping("/setExitDataPension")
	public ResponseEntity<String> setExitDataPension(@RequestParam("token") int token) {
		boolean tokenExists = estacionamientoService.existToken(token);
		if (tokenExists) {
			boolean estacionamiento = estacionamientoService.setExitDataEstacionamientoPensionWithToken(token);
			if (!estacionamiento) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body("No se pudo actualizar estacionamiento con token: " + token);
			}
			registryService.setRegistry3Exit(token);
			return ResponseEntity.ok("Estacionamiento con token: " + token + " actualizado");
		} else
			registryService.setRegistry2proccess();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el estacionamiento con token: " + token);
	}
	// se mantiene el borrado normal y el conseguir la fecha/hora de entrada

	@GetMapping("/lastId")
	public int getLatToken() {
		return registryService.getLastTokenRegistry();
	}

	@GetMapping("/ingreso")
	public BienvenidaDTO getHoraIngreso() {
		int token = registryService.getLastTokenRegistry();
		LocalDateTime horaIngreso = estacionamientoService.getEstacionamientobyToken(token);

		// Formatear la fecha y hora por separado
		DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");

		String fechaFormateada = horaIngreso.format(formatterFecha);
		String horaFormateada = horaIngreso.format(formatterHora);

		BienvenidaDTO bienvenidaDTO = new BienvenidaDTO();
		bienvenidaDTO.setFecha(fechaFormateada);
		bienvenidaDTO.setHora(horaFormateada);

		return bienvenidaDTO;
	}

	@GetMapping("/getAllEstacionamientos")
	public List<Estacionamiento> findAllEstacionamientos() {
		return estacionamientoDao.findAllEstacionamientos();
	}

	@GetMapping("deleteEstacionamientoById")
	public void deleteEstacionamientoById(@RequestParam Long id) {
		estacionamientoDao.deleteEstacionamientoById(id);
	}

}
