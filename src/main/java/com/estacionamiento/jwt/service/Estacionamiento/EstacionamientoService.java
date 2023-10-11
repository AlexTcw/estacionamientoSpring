package com.estacionamiento.jwt.service.Estacionamiento;

import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.model.Dto.IngresoDTO;
import com.estacionamiento.jwt.model.Dto.ReciboDTO;

@Service
public interface EstacionamientoService {

	Boolean existToken(String token);

	ReciboDTO generarReciboSalidaEstacionamiento(String token);

	IngresoDTO controlEntrada(String token);

}
