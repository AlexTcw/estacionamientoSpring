package com.estacionamiento.jwt.service.Estacionamiento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.model.Estacionamiento;
import com.estacionamiento.jwt.model.DTO.IngresoDTO;
import com.estacionamiento.jwt.model.DTO.PagoInfoDto;
import com.estacionamiento.jwt.model.DTO.ReciboDTO;

@Service
public interface EstacionamientoService {

	int generateNewToken();

	Boolean existToken(int token);

	// ReciboDTO generarReciboSalidaEstacionamiento(int token);

	Boolean createNewEstacionamientoWithToken(int token);

	Boolean setExitDataEstacionamientoWithToken(int token);

	Duration calculateDuration(LocalDateTime ingreso, LocalDateTime salida);

	Estacionamiento getEstacionamientoByToken(int token);

	public Boolean setExitDataEstacionamientoPensionWithToken(int token);

	PagoInfoDto getPagoInfo(int token);

	// ReciboDTO SetDatosDeSalidaInEstacionamiento(int token, Boolean edo, Long
	// edoUsu);

	// IngresoDTO controlEntrada(int token);

	// Boolean createUsu(Long edoUsu, int token);

	Long getLastToken();

	LocalDateTime getEstacionamientobyToken(int token);

	boolean changeToHistorial(int token, double timepoUso, double total);

	boolean cambiaEdoPago(int token, long edoUsu);

	Estacionamiento updEstacionamiento(double total, LocalDateTime salida, int token);

	void deleteEstacionamientoByToken(int token);

	// public Boolean createGerericUsuario(Long edoUsu, int token);

}
