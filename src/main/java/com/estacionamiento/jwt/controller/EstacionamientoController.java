package com.estacionamiento.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamiento.jwt.model.DTO.IngresoDTO;
import com.estacionamiento.jwt.model.DTO.ReciboDTO;
import com.estacionamiento.jwt.service.Estacionamiento.EstacionamientoService;

@RestController
public class EstacionamientoController {

	@Autowired
	EstacionamientoService estacionamientoService;
	
	/*"http://192.168.1.77:8080/api/fingerprint/generateToken"*/
	
	@GetMapping("/generateToken")
	public int generateTokenFromFP() {
		return estacionamientoService.generateNewToken();
	}
	
	@PostMapping("/saveOrDelete")
	public ResponseEntity<Object> gestionarToken(   
			@RequestParam("token") int token,
		    @RequestParam(value = "edopago", required = false) Boolean edopago,
		    @RequestParam(value = "edoUsu", required = false) Long edoUsu) {
		
	    boolean tokenExistente = estacionamientoService.existToken(token);

	    if (tokenExistente) {
	    	ReciboDTO reciboDTO = estacionamientoService.generarReciboSalidaEstacionamiento(token);
	    	return ResponseEntity.ok(reciboDTO);
	    } else {
	        IngresoDTO ingresoDto = estacionamientoService.controlEntrada(token);
	        if (ingresoDto == null) {
				return (ResponseEntity<Object>) ResponseEntity.badRequest();
			}
	        return ResponseEntity.ok(ingresoDto);
	    }
	}
}
