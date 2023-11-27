package com.estacionamiento.jwt.service.Estacionamiento;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.model.Estacionamiento;
import com.estacionamiento.jwt.model.DTO.IngresoDTO;
import com.estacionamiento.jwt.model.DTO.PagoDto;
import com.estacionamiento.jwt.model.DTO.ReciboDTO;

@Service
public interface EstacionamientoService {

	int generateNewToken();

	Boolean existToken(int token);

	//ReciboDTO generarReciboSalidaEstacionamiento(int token);

	IngresoDTO controlEntrada(int token);

	ReciboDTO generarReciboSalidaEstacionamiento(int token, Boolean edo, Long edoUsu);

	Boolean createUsu(Long edoUsu, int token);

	Long getLastToken();

	LocalDateTime getEstacionamientobyToken(int token);

	PagoDto getpago(int token);

	boolean changeToHistorial(int token);

	boolean cambiaEdoPago(int token,long edoUsu);

	Estacionamiento updEstacionamiento(double total, LocalDateTime salida, int token);

	void deleteEstacionamientoByToken(int token);

}
