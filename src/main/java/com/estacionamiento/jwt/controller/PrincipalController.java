package com.estacionamiento.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamiento.jwt.model.UsrPrincipal;
import com.estacionamiento.jwt.service.UsrPrincipalService;

@RestController
@Controller
public class PrincipalController {
	
	@Autowired
	UsrPrincipalService usrPrincipalService;
	
	@GetMapping({"","/Ingreso"})
	public UsrPrincipal nuevoIngreso(@RequestParam String tokenString) {
		
		UsrPrincipal usrPrincipal = new UsrPrincipal();
		usrPrincipalService.SavePrincipal(usrPrincipal);
		
		return usrPrincipal;
	}
	
}
