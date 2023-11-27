package com.estacionamiento.jwt.service.Estacionamiento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.Dao.Estacionamiento.EstacionamientoDao;
import com.estacionamiento.jwt.Dao.Historial.HistorialDao;
import com.estacionamiento.jwt.Dao.Usuario.UsuarioDao;
import com.estacionamiento.jwt.model.Const;
import com.estacionamiento.jwt.model.Estacionamiento;
import com.estacionamiento.jwt.model.Historial;
import com.estacionamiento.jwt.model.Usuario;
import com.estacionamiento.jwt.model.DTO.IngresoDTO;
import com.estacionamiento.jwt.model.DTO.PagoDto;
import com.estacionamiento.jwt.model.DTO.ReciboDTO;

@Service
public class EstacionamientoServiceImp implements EstacionamientoService {

	@Autowired
	EstacionamientoDao estDao;

	@Autowired
	HistorialDao hstDao;

	@Autowired
	UsuarioDao usrDao;

	@Override
	public Long getLastToken() {
		return estDao.findLastToken();
	}

	/* valida si ya existe o no el token */
	@Override
	public Boolean existToken(int token) {
		Estacionamiento est = estDao.findEstacionamientoByTokenIngreso(token);
		if (est != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int generateNewToken() {
		Long lastId = estDao.findLastId();
		if (lastId != null) {
			return lastId.intValue() + 1;
		} else {
			lastId = 1L;
			return lastId.intValue();
		}
	}

	@Override
	public Boolean createUsu(Long edoUsu, int token) {

		if (edoUsu != null && edoUsu == 1L) {
			Usuario usuario = new Usuario();
			usuario.setContraseña("11223344");
			usuario.setEdoUsu(edoUsu);
			usuario.setCorreo("generico@Usuario.com");
			usuario.setTokenEst(token);
			usuario.setEdoUsu(1L);

			usrDao.createOrUpdateUsuario(usuario);

			return true;
		}
		return false;
	}

	/* se accionara si el usuario ya existia y fue detectado por la huella */
	@Override
	public ReciboDTO generarReciboSalidaEstacionamiento(int token, Boolean edo, Long edoUsu) {

		Const costoPH = new Const();
		Usuario usr = usrDao.findUsuarioByToken(token);
		Estacionamiento est = estDao.findEstacionamientoByTokenIngreso(token);

		/* recupera la entrada y establece la salida del usuario */
		LocalDateTime ingreso = est.getIngresoFec();
		LocalDateTime salida = LocalDateTime.now();
		est.setSalidaFec(salida);

		/* calcula el costo por uso */
		Duration duration = Duration.between(ingreso, salida);
		long hrs = duration.toHours();
		long mnts = duration.toMinutes();
		long sec = duration.toSeconds();

		double totalHoras;
		double totalTiempo;

		if (sec >= 0) {
			// Si los minutos son múltiplos de 60 o hay segundos, redondear hacia arriba
			totalTiempo = Math.ceil((double) sec / 60);
			totalHoras = Math.ceil((double) sec / 60) * costoPH.costoPorHora;
			System.out.println(totalTiempo);
		} else {
			// Si los minutos son exactos, simplemente usar las horas
			totalTiempo = hrs;
			totalHoras = hrs * costoPH.costoPorHora;
		}

		/* Verificamos si el usuario ya salio */
		if (edo != null && edo == true) {
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
			hst.setTiempoDeUso(totalTiempo);
			hst.setTotal(TotalEst);
			hst.setCveEst(est.getCveEst());

			hstDao.saveOrUpdateHistorial(hst);

			/* Borramos de usuario despues del traslado */
			estDao.deleteEstacionamientoByToken(token);

			return recibo;
		}

		return null;

	}

	@Override
	public IngresoDTO controlEntrada(int token) {
		LocalDateTime ingreso = LocalDateTime.now();
		IngresoDTO recibo = new IngresoDTO();

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

		/* creamos al usuario generico */
		Usuario usr = new Usuario();
		usr.setTokenEst(token);
		usrDao.createOrUpdateUsuario(usr);

		recibo.setIngreso(ingreso);
		recibo.setMensaje("bienvenido al estacionamiento");

		return recibo;
	}

	@Override
	public LocalDateTime getEstacionamientobyToken(int token) {
		Estacionamiento est = estDao.findEstacionamientoByTokenIngreso(token);

		LocalDateTime Ingreso = est.getIngresoFec();
		return Ingreso;
	}

	@Override
	public PagoDto getpago(int token) {
		Estacionamiento est = estDao.findEstacionamientoByTokenIngreso(token);
		Const costoPH = new Const();
		PagoDto pago = new PagoDto();

		/* recupera la entrada y establece la salida del usuario */
		LocalDateTime ingreso = est.getIngresoFec();
		LocalDateTime salida = LocalDateTime.now();
		est.setSalidaFec(salida);

		/* calcula el costo por uso */
		Duration duration = Duration.between(ingreso, salida);
		long hrs = duration.toHours();
		long sec = duration.toSeconds();

		double totalHoras;

		Usuario usuario = usrDao.findUsuarioByToken(token);
		if (usuario.getEdoUsu() == 1L) {
			if (sec >= 0 && sec < 3600) {
				// Si los minutos son múltiplos de 60 o hay segundos, redondear hacia arriba
				totalHoras = Math.ceil((double) sec / 3600) * (costoPH.costoPorHora - 17);
			} else {
				// Si los minutos son exactos, simplemente usar las horas
				totalHoras = hrs * (costoPH.costoPorHora - 17);
			}
			pago.setSubTotal(totalHoras);
			pago.setTotal(Math.ceil(totalHoras * 1.16));
		} else {
			if (sec >= 0 && sec < 3600) {
				// Si los minutos son múltiplos de 60 o hay segundos, redondear hacia arriba
				totalHoras = Math.ceil((double) sec / 3600) * costoPH.costoPorHora;
			} else {
				// Si los minutos son exactos, simplemente usar las horas
				totalHoras = hrs * costoPH.costoPorHora;
			}
			pago.setSubTotal(totalHoras);
			pago.setTotal(Math.ceil(totalHoras * 1.16));
		}
		pago.setFechaEntradaString(ingreso.toString());
		pago.setFechaSalida(salida.toString());
		pago.setFechaCompleta(salida);

		return pago;
	}

	@Override
	public boolean cambiaEdoPago(int token, long edoUsu) {
		Estacionamiento est = estDao.findEstacionamientoByTokenIngreso(token);

		if (edoUsu != 1) {
			return false;
		}

		est.setEdoPago(true);
		estDao.CreateOrUpdateEstacionamiento(est);
		return true;
	}

	@Override
	public boolean changeToHistorial(int token, double timepoUso,double total) {
		Estacionamiento estacionamiento = estDao.findEstacionamientoByTokenIngreso(token);

		if (estacionamiento != null) {
			Historial historial = new Historial();
			historial.setCveEst(estacionamiento.getCveEst());
			historial.setIngresoFec(estacionamiento.getIngresoFec());
			historial.setSalidaFec(estacionamiento.getSalidaFec());

			historial.setTiempoDeUso(timepoUso);

			historial.setTotal(total);

			hstDao.saveOrUpdateHistorial(historial);
			return true;
		} else {
			return false; // O manejar de otra manera si el estacionamiento no existe
		}
	}

	@Override
	public Estacionamiento updEstacionamiento(double total, LocalDateTime salida, int token) {
		Estacionamiento estacionamiento = estDao.findEstacionamientoByTokenIngreso(token);
		estacionamiento.setSalidaFec(salida);
		estacionamiento.setTotal(total);
		estDao.CreateOrUpdateEstacionamiento(estacionamiento);
		return estacionamiento;
	}

	@Override
	public void deleteEstacionamientoByToken(int token) {
		estDao.deleteEstacionamientoByToken(token);
	}

}
