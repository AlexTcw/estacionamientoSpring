package com.estacionamiento.jwt.service;

import com.estacionamiento.jwt.model.UsrPrincipal;
import com.estacionamiento.jwt.model.Dto.IngresoDTO;
import com.estacionamiento.jwt.model.Dto.ReciboDTO;

public interface UsrPrincipalService {

	UsrPrincipal SavePrincipal(UsrPrincipal usrPrincipal);

	ReciboDTO ControlSalida(String token);

	IngresoDTO ControlEntrada(String token);

}
