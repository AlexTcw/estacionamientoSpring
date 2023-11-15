package com.estacionamiento.jwt.service.Estacionamiento;

import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.model.DTO.IngresoDTO;
import com.estacionamiento.jwt.model.DTO.ReciboDTO;

@Service
public interface EstacionamientoService {

	int generateNewToken();

	Boolean existToken(int token);

	ReciboDTO generarReciboSalidaEstacionamiento(int token);

	IngresoDTO controlEntrada(int token);

}
