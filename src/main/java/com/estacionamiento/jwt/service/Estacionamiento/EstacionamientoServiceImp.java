package com.estacionamiento.jwt.service.Estacionamiento;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
import com.estacionamiento.jwt.model.DTO.PagoInfoDto;
import com.estacionamiento.jwt.model.DTO.ReciboDTO;

@Service
public class EstacionamientoServiceImp implements EstacionamientoService {

	@Autowired
	EstacionamientoDao estDao;

	@Autowired
	HistorialDao hstDao;

	@Autowired
	UsuarioDao usrDao;

	private static final int SECONDS_IN_HOUR = 3600;

	@Override
	public Long getLastToken() {
		return estDao.findLastToken();
	}

	// as608 function
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

	// metodos especiales
	// calcula el costo por usar el estacionamiento desde el primer segundo
	private Double calculateCosto(LocalDateTime ingreso, LocalDateTime salida) {
		Const costoPorHora = new Const();
		Duration duration = Duration.between(ingreso, salida);

		long segundosTotales = duration.getSeconds();
		long horas = duration.toHoursPart();
		long minutos = duration.toMinutesPart();
		long segundosRestantes = duration.toSecondsPart();

		System.out.println("ingreso: " + ingreso + " salida: " + salida);
		System.out.println(horas + " horas, " + minutos + " minutos, " + segundosRestantes + " segundos");

		if (segundosTotales >= 0 && segundosTotales < SECONDS_IN_HOUR) {
			// Si la duración es menor a una hora, calcular el costo por minuto
			return costoPorHora.costoPorHora;
		} else {
			// Si la duración es igual o mayor a una hora, calcular el costo por hora
			return horas * costoPorHora.costoPorHora;
		}
	}

	// calcula la diferencia entre hora salida y entrada
	@Override
	public Duration calculateDuration(LocalDateTime ingreso, LocalDateTime salida) {
		return Duration.between(ingreso, salida);
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

	// Estacionamiento Path
	/*
	 * edoUsu
	 * 0=generico
	 * 1=pensionado
	 * 2= admin
	 */
	// crear Registro en tabla Estacionamiento
	@Override
	public Boolean createNewEstacionamientoWithToken(int token) {
		// Obtenemos la fecha de ingreso
		LocalDateTime ingresoDateTime = LocalDateTime.now();
		/* maxID mejorar la legibilidad de la tabla */
		Long maxCveEst = estDao.maxID();
		if (maxCveEst == null) {
			maxCveEst = 0L;
		}
		Estacionamiento est = new Estacionamiento();
		est.setTokenIngreso(token);
		est.setIngresoFec(ingresoDateTime);
		estDao.CreateOrUpdateEstacionamiento(est);
		return true;
	}

	// Busca los datos para el front
	@Override
	public PagoInfoDto getPagoInfo(int token) {
		Estacionamiento est = estDao.findEstacionamientoByTokenIngreso(token);
		Usuario usuario = usrDao.findUsuarioByToken(token);

		if (est == null) {
			return null;
		}

		PagoInfoDto pagoInfo = new PagoInfoDto();

		LocalDateTime ingreso = est.getIngresoFec();
		LocalDateTime actual = LocalDateTime.now();

		Double total = calculateCosto(ingreso, actual);
		LocalTime horaEntrada = ingreso.toLocalTime();

		if (usuario != null && usuario.getEdoUsu() == 1L) {
			// Utilizar una constante para representar el estado activo del usuario
			total *= 0.7;
		}

		DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/y");

		String horaEntradaFormat = horaEntrada.format(formatterHora);

		pagoInfo.setFecha(ingreso.format(formatter));
		pagoInfo.setFechaEntrada(horaEntradaFormat);
		pagoInfo.setFechaCompleta(ingreso);
		pagoInfo.setTotal(Double.parseDouble(String.format("%.2f", total)));
		pagoInfo.setSubTotal(Double.parseDouble(String.format("%.2f", total * 0.84)));

		return pagoInfo;
	}

	// Establece los datos del usuario que quiere salir con el token
	@Override
	public Boolean setExitDataEstacionamientoWithToken(int token) {
		Estacionamiento est = estDao.findEstacionamientoByTokenIngreso(token);

		if (est == null) {
			// Estacionamiento no encontrado, considerar lanzar una excepción o devolver un
			// resultado específico
			return false;
		}

		LocalDateTime fechaIngreso = est.getIngresoFec();
		LocalDateTime fechaSalida = LocalDateTime.now();
		Double total = calculateCosto(fechaIngreso, fechaSalida);

		est.setSalidaFec(fechaSalida);
		est.setTotal(total);
		est.setEdoPago(true);

		estDao.CreateOrUpdateEstacionamiento(est);

		return true;
	}

	// se borrara una vez que se registren los datos en salida
	@Override
	public void deleteEstacionamientoByToken(int token) {
		estDao.deleteEstacionamientoByToken(token);
	}

	// Buscar estacionamiento de ser necesario
	@Override
	public Estacionamiento getEstacionamientoByToken(int token) {
		return estDao.findEstacionamientoByTokenIngreso(token);
	}

	// Pensionado Path
	// Establece los datos del estacionamiento con costo reducido
	@Override
	public Boolean setExitDataEstacionamientoPensionWithToken(int token) {
		Estacionamiento est = estDao.findEstacionamientoByTokenIngreso(token);
		Usuario usuario = usrDao.findUsuarioByToken(token);

		if (est == null || usuario == null) {
			return false;
		}

		if (usuario.getEdoUsu() != 1L) {
			return false;
		}

		LocalDateTime fechaIngreso = est.getIngresoFec();
		LocalDateTime fechaSalida = LocalDateTime.now();
		Double total = calculateCosto(fechaIngreso, fechaSalida);
		total = total * 0.7;

		est.setSalidaFec(fechaSalida);
		est.setTotal(total);
		est.setEdoPago(true);

		estDao.CreateOrUpdateEstacionamiento(est);

		return true;
	}
	// Se mantiene el borrado igual que el de Estacionamiento Normal

	// Admin capabilities
	// recuperar todos los estacionamientos
	@Override
	public List<Estacionamiento> getAllEstacionamientoTbl() {
		return estDao.findAllEstacionamientos();
	}

	// actualizar estacionamiento
	@Override
	public Estacionamiento updEstacionamiento(double total, LocalDateTime salida, int token) {
		Estacionamiento estacionamiento = estDao.findEstacionamientoByTokenIngreso(token);
		estacionamiento.setSalidaFec(salida);
		estacionamiento.setTotal(total);
		estDao.CreateOrUpdateEstacionamiento(estacionamiento);
		return estacionamiento;
	}

	@Override
	public void deleteEstacionamientoByCve(Long cve) {
		estDao.deleteEstacionamientoById(cve);
	}

}
