package com.estacionamiento.jwt.service.Estacionamiento;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.dao.Estacionamiento.EstacionamientoDao;
import com.estacionamiento.jwt.dao.Historial.HistorialDao;
import com.estacionamiento.jwt.dao.usuario.UsuarioDao;
import com.estacionamiento.jwt.model.Const;
import com.estacionamiento.jwt.model.Estacionamiento;
import com.estacionamiento.jwt.model.Historial;
import com.estacionamiento.jwt.model.Usuario;
import com.estacionamiento.jwt.model.Dto.IngresoDTO;
import com.estacionamiento.jwt.model.Dto.ReciboDTO;

@Service
public class EstacionamientoServiceImp implements EstacionamientoService {

	@Autowired
	EstacionamientoDao estDao;

	@Autowired
	HistorialDao hstDao;

	@Autowired
	UsuarioDao usrDao;

	/* valida si ya existe o no el token */
	@Override
	public Boolean existToken(String token) {
		Estacionamiento est = estDao.findEstacionamientoByTokenIngreso(token);
		if (est != null) {
			return true;
		} else {
			return false;
		}
	}

	/* se accionara si el usuario ya existia y fue detectado por la huella */
	@Override
	public ReciboDTO generarReciboSalidaEstacionamiento(String token) {

		Const costoPH = new Const();
		Usuario usr = usrDao.findUsuarioByToken(token); 
		Estacionamiento est = estDao.findEstacionamientoByTokenIngreso(token);
		

		/* recupera la entrada y establece la salida del usuario */
		LocalDateTime ingreso = est.getIngresoFec();
		LocalDateTime salida = LocalDateTime.now();
		est.setSalidaFec(salida);

		/* calcula el costo por uso */
		Duration duration = Duration.between(ingreso, salida);
		Long hrs = duration.toHours();
		Long mnts = duration.toMinutes();

		double totalHoras = hrs * costoPH.costoPorHora;
		if (totalHoras == 0) {
			totalHoras = mnts * costoPH.costoPorMinuto;
		}
		est.setTotal(totalHoras);
		estDao.CreateOrUpdateEstacionamiento(est);

		/* Generamos el recibo */
		ReciboDTO recibo = new ReciboDTO();
		Double TotalEst = est.getTotal();
		// setCorreo
		recibo.setCorreo(usr.getCorreo());
		recibo.setTotalHoras(hrs);
		recibo.setTotalMinutos(mnts);
		recibo.setMensaje("Gracias Por su visita");
		recibo.setTotalCosto(TotalEst);
		/* guardamos los cambios a la entidad Historial */
		Historial hst = new Historial();
		hst.setIngresoFec(ingreso);
		hst.setSalidaFec(salida);
		hst.setTiempoDeUso(totalHoras);
		hst.setTotal(TotalEst);
		hst.setCveEst(est.getCveEst());

		hstDao.saveOrUpdateHistorial(hst);

		/* Borramos de usuario despues del traslado */
		estDao.deleteEstacionamientoByToken(token);

		return recibo;

	}

	@Override
	public IngresoDTO controlEntrada(String token) {
		LocalDateTime ingreso = LocalDateTime.now();
		Const Const = new Const();
		IngresoDTO recibo = new IngresoDTO();

		/* Generar Token */
		token = UUID.randomUUID().toString();
		/* maxID */
		Long maxCveEst = estDao.maxID();

		if (maxCveEst == null) {
			maxCveEst = 0L;
		}

		/* Crear nuevo Estacionamiento */

		Estacionamiento est = new Estacionamiento();
		est.setTokenIngreso(token);
		est.setIngresoFec(ingreso);
		/* como jalo el correo y se lo establezco al Estacionamiento */

		estDao.CreateOrUpdateEstacionamiento(est);

		recibo.setIngreso(ingreso);
		recibo.setMensaje("bienvenido al estacionamiento");

		return recibo;

	}

}
