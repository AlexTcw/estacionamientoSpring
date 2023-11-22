package com.estacionamiento.jwt.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamiento.jwt.model.DTO.BienvenidaDTO;
import com.estacionamiento.jwt.model.DTO.IngresoDTO;
import com.estacionamiento.jwt.model.DTO.PagoDto;
import com.estacionamiento.jwt.model.DTO.ReciboDTO;
import com.estacionamiento.jwt.service.Estacionamiento.EstacionamientoService;
import com.estacionamiento.jwt.service.registry.RegistryService;

@RestController
@CrossOrigin(origins = { "*" })
public class EstacionamientoController {

	@Autowired
	RegistryService registryService;
	
	@Autowired
	EstacionamientoService estacionamientoService;

	/* "http://192.168.1.77:8080/api/fingerprint/generateToken" */

	@GetMapping("/generateToken")
	public int generateTokenFromFP() {
		return estacionamientoService.generateNewToken();
	}

	@PostMapping("/saveOrDelete")
	public ResponseEntity<Object> gestionarToken(@RequestParam("token") int token,
			@RequestParam(value = "edopago", required = false) Boolean edopago,
			@RequestParam(value = "edoUsu", required = false) Long edoUsu) {

		boolean tokenExistente = estacionamientoService.existToken(token);

		if (tokenExistente) {
			if (edopago != null && edoUsu == 1L) {
				estacionamientoService.createUsu(edoUsu, token);
			}
			
			/*ReciboDTO reciboDTO = estacionamientoService.generarReciboSalidaEstacionamiento(token, edopago, edoUsu);			
			return ResponseEntity.ok(reciboDTO);*/			
			
			registryService.setRegistry3Exit(token);		
			return null;
		} else {
			IngresoDTO ingresoDto = estacionamientoService.controlEntrada(token);
			if (ingresoDto == null) {
				return (ResponseEntity<Object>) ResponseEntity.badRequest();
			}
			registryService.setRegistry1Enrroll(token);
			return ResponseEntity.ok(ingresoDto);
		}
	}	
	
	@GetMapping("/try-pay")
	public PagoDto tryPay(@RequestParam("token")int token) {
		token = registryService.getLastTokenRegistry();
		PagoDto pago = estacionamientoService.getpago(token);
		return pago;
		}
	
	@GetMapping("/already-pay") 
	PagoDto pagado(@RequestParam("pago") PagoDto pago) {
		return null;
	}

	@GetMapping("/lastId")
	public int getLatToken() {
		return registryService.getLastTokenRegistry();
	}
	
	@GetMapping("/pago")
	public PagoDto getPagoInfo(@RequestParam("token") int token) {
		token = registryService.getLastTokenRegistry();
		return estacionamientoService.getpago(token);
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
}
