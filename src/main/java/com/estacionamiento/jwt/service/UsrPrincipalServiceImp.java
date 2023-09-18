package com.estacionamiento.jwt.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.dao.UsrPrincipalDao.UsrPrincipalDao;
import com.estacionamiento.jwt.model.UsrPrincipal;
import com.estacionamiento.jwt.model.Dto.IngresoDTO;
import com.estacionamiento.jwt.model.Dto.ReciboDTO;
import com.estacionamiento.jwt.model.Const;

import java.time.Duration;

@Service
public class UsrPrincipalServiceImp implements UsrPrincipalService {

	@Autowired
	UsrPrincipalDao usrPrincipalDao;

	@Override
	public UsrPrincipal SavePrincipal(UsrPrincipal usrPrincipal) {
		return usrPrincipalDao.saveorUpdateUPD(usrPrincipal);
	}

	@Override
	public ReciboDTO ControlSalida(String token){
		ReciboDTO recibo = new ReciboDTO();

		UsrPrincipal usr = usrPrincipalDao.recuperarUsrPrincipalByToken(token);

			Const costo = new Const();

			/* Establecer entrada y salida del usuario */
			LocalDateTime ingreso = usr.getIngreso();
			LocalDateTime salida = LocalDateTime.now();
			usr.setSalida(salida);
			/* calcular duracion en el estacionamiento */
			Duration duracion = Duration.between(ingreso, salida);
			long horas = duracion.toHours();
			Long minutos = duracion.toMinutes();

			double totalHoras = horas * costo.costoPorHora;
			if (totalHoras == 0){
				totalHoras = minutos*costo.costoPorMinuto;
			}

			usr.setTotal(totalHoras);

			usrPrincipalDao.saveorUpdateUPD(usr);

			recibo.setIdPublica(usr.getIdPublica());
			recibo.setTotalHoras(horas);
			recibo.setTotalMinutos(minutos);
			recibo.setMensaje("Gracias por su visita");
			recibo.setTotalCosto(usr.getTotal());



		return recibo;

	}

	@Override
	public IngresoDTO ControlEntrada(String token) {
		IngresoDTO ingresoDTO = new IngresoDTO();

		/* Generacion del token */
		token = UUID.randomUUID().toString();
		Long id = usrPrincipalDao.maxId();

		if (id == null) {
			id = 0L;
		}
		/* creacion del usuario */
		UsrPrincipal usr = new UsrPrincipal();
		usr.setTokenIngreso(token);

		LocalDateTime ingreso = LocalDateTime.now();
		usr.setIngreso(ingreso);

		Const idPub = new Const();

		String idPublica = idPub.idPublicaGenerator();
		usr.setIdPublica(idPublica);

		usrPrincipalDao.saveorUpdateUPD(usr);

		ingresoDTO.setIdPublica(idPublica);
		ingresoDTO.setIngreso(ingreso);
		ingresoDTO.setMensaje("Bienvenido al uso del estacionamiento"+id);

		return ingresoDTO;
		
	}

}
