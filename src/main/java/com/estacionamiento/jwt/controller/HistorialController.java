package com.estacionamiento.jwt.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamiento.jwt.model.Estacionamiento;
import com.estacionamiento.jwt.model.Historial;
import com.estacionamiento.jwt.service.Estacionamiento.EstacionamientoService;
import com.estacionamiento.jwt.service.Historial.HistorialService;

@RestController
@CrossOrigin(origins = { "*" })
public class HistorialController {

    @Autowired 
    HistorialService historialService;

    @Autowired
    EstacionamientoService estacionamientoService;

    @GetMapping("/getAllHistorials")
    public List<Historial> getAllHistorials(){
        return historialService.findAllHistorials();
    }
    
@GetMapping("/transferHist")
    public ResponseEntity<String> transferHistorial(@RequestParam int token) {
        try {
            Long edoUsu = 1L;
            LocalDateTime fechaHoraActuales = LocalDateTime.now();

            Boolean changeEdo = estacionamientoService.cambiaEdoPago(token, edoUsu);

            if (changeEdo) {
                Estacionamiento estacionamiento = estacionamientoService.updEstacionamiento(token, fechaHoraActuales, token);

                if (estacionamiento != null) {
                    Boolean changeToHistorial = estacionamientoService.changeToHistorial(token);

                    if (changeToHistorial) {
                        estacionamientoService.deleteEstacionamientoByToken(token);
                        return ResponseEntity.ok("Usuario transferido");
                    } else {
                        return ResponseEntity.ok("Estacionamiento no transferido");
                    }
                } else {
                    return ResponseEntity.ok("Estacionamiento no actualizado");
                }
            } else {
                return ResponseEntity.ok("Estado no cambiado");
            }
        } catch (Exception e) {
            // Manejar cualquier excepción aquí, ya sea registrándola o devolviendo una respuesta específica.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }
}
