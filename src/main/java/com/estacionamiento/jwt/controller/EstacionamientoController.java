package com.estacionamiento.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamiento.jwt.service.Estacionamiento.EstacionamientoService;

@Controller
@RestController
public class EstacionamientoController {

	@Autowired
	EstacionamientoService estacionamientoService;

    @PostMapping("/ingreso")
    public Object controlEntrada(@RequestParam(value = "token", required = false) String token) {
        if (token == null) {
            // Si no se proporciona un token, crea uno nuevo y devuelve un mensaje de bienvenida.
            return estacionamientoService.controlEntrada(null);
        } else {
            // Si se proporciona un token existente, busca el estacionamiento y devuelve el recibo de salida.
            return estacionamientoService.generarReciboSalidaEstacionamiento(token);
        }
    }
}
