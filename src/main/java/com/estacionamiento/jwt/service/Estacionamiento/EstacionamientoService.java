package com.estacionamiento.jwt.service.Estacionamiento;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.model.Estacionamiento;
import com.estacionamiento.jwt.model.DTO.IngresoDTO;
import com.estacionamiento.jwt.model.DTO.PagoInfoDto;
import com.estacionamiento.jwt.model.DTO.ReciboDTO;

@Service
public interface EstacionamientoService {

	int generateNewToken();

	Boolean existToken(int token);

	List<Estacionamiento> getAllEstacionamientoTbl();

	public Estacionamiento getEstacionamientoByToken(int token);

	// ReciboDTO generarReciboSalidaEstacionamiento(int token);

	Boolean createNewEstacionamientoWithToken(int token);

	Boolean setExitDataEstacionamientoWithToken(int token);

	Duration calculateDuration(LocalDateTime ingreso, LocalDateTime salida);

	public Boolean setExitDataEstacionamientoPensionWithToken(int token);

	PagoInfoDto getPagoInfo(int token);

	// ReciboDTO SetDatosDeSalidaInEstacionamiento(int token, Boolean edo, Long
	// edoUsu);

	// IngresoDTO controlEntrada(int token);

	// Boolean createUsu(Long edoUsu, int token);

	Long getLastToken();

	Estacionamiento updEstacionamiento(double total, LocalDateTime salida, int token);

	void deleteEstacionamientoByToken(int token);

	public void deleteEstacionamientoByCve(Long cve);

	// public Boolean createGerericUsuario(Long edoUsu, int token);

}
