package com.estacionamiento.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamiento.jwt.model.Dto.IngresoDTO;
import com.estacionamiento.jwt.model.Dto.ReciboDTO;
import com.estacionamiento.jwt.service.UsrPrincipalService;

@RestController
@Controller
public class PrincipalController {

	@Autowired
	UsrPrincipalService usrPrincipalService;

	@GetMapping("/control-Ingreso")
    public ResponseEntity<Object> ControlIngreso(@RequestParam(name = "token", required = false) String token) {
        if (token == null) {
            IngresoDTO recibo = usrPrincipalService.ControlEntrada(token);
            return ResponseEntity.ok(recibo);
        } else {
            ReciboDTO ingreso = usrPrincipalService.ControlSalida(token);
            return ResponseEntity.ok(ingreso);
        }
    }

}
