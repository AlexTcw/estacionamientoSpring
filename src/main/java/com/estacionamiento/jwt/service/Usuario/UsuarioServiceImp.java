package com.estacionamiento.jwt.service.Usuario;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.Dao.Estacionamiento.EstacionamientoDao;
import com.estacionamiento.jwt.Dao.Usuario.UsuarioDao;
import com.estacionamiento.jwt.model.Estacionamiento;
import com.estacionamiento.jwt.model.Usuario;
import com.estacionamiento.jwt.model.DTO.UsrInfoDto;
import com.estacionamiento.jwt.service.registry.RegistryService;

@Service
public class UsuarioServiceImp implements UsuarioService {

	@Autowired
	UsuarioDao usudao;

	@Autowired
	EstacionamientoDao estDao;

	@Autowired
	RegistryService registryService;

	// UserExtraMethods
	@Override
	public Boolean findUsuarioByCorreoUsu(String correo) {
		Usuario usuario = usudao.findUsuarioByCorreo(correo);

		if (usuario != null) {
			return true; // El usuario existe en la base de datos
		} else {
			return false; // El usuario no existe en la base de datos
		}
	}

	@Override
	public int getUsuarioTokenByCorreoAndPSW(String correo, String contraseña) {
		Usuario usuario = usudao.findUsuarioByCorreo(correo);

		if (usuario != null) {
			// Utiliza el método equals para comparar cadenas
			if (usuario.getContraseña().equals(contraseña)) {
				return usuario.getTokenEst();
			}
			return 0;
		}

		return 0;
	}

	@Override
	public Boolean existUsuario(String correo, String pass, int token) {
		return usudao.existUsuarioByCorreoAndContraseñaAndTokenEst(correo, pass, token);
	}

	/*
	 * 0=generico
	 * 1=pensionado
	 * 2= admin
	 */
	// Pensionado Path
	// crear un nuevo Usuario Pensionado con token inmutable
	@Override
	public Usuario createNewPensionUsuario(String correo, String pswd, int token, String placa, String nombre) {
		Boolean existUsuario = usudao.existUsuarioByCorreo(correo);
		if (existUsuario) {
			return null;
		}
		Usuario usuario = new Usuario(null, 1L, correo, pswd, Collections.singletonList(placa), token, nombre);
		try {
			usudao.createOrUpdateUsuario(usuario);
		} catch (Exception e) {
			// Manejar la excepción apropiadamente
			e.printStackTrace(); // o loguear el error
			return null; // O manejar el error de alguna otra manera
		}

		return usuario;
	}

	// Recuperamos los datos importantes del usuario
	@Override
	public UsrInfoDto getUsrInfo(int token) {

		UsrInfoDto usrInfoDto = new UsrInfoDto();
		Usuario usuario = usudao.findUsuarioByToken(token);
		if (usuario != null) {
			List<String> placas = usuario.getPlacas();
			usrInfoDto.setNombreUsu(usuario.getNomUsu());
			usrInfoDto.setCorreoUsu(usuario.getCorreo());
			usrInfoDto.setPlacas(placas);
			usrInfoDto.setToken(token);

			return usrInfoDto;

		}
		return null;
	}

	/* Login path */
	// recuperamos el estado del usuario
	/*
	 * 0=generico sin loby token mutable
	 * 1=pensionado loby pension token inmutable
	 * 2= admin loby Administrador loby inalcanzable por sistema token 0
	 */
	@Override
	public Long getEdoUsuarioByTokenForPension(int token) {
		Usuario usuario = usudao.findUsuarioByToken(token);

		if (usuario == null) {
			return null;
		}

		Long edoUsu = usuario.getEdoUsu();

		return edoUsu; // Devuelve edoUsu directamente (puede ser null si usuario.getEdoUsu() es null)
	}

	// Si no tiene acceso a la huella o simplemente no quiere
	@Override
	public Boolean validateUsuarioPension(String correo, String contraseña) {
		Usuario usuario = usudao.findUsuarioByCorreo(correo);

		// Verificar si el usuario existe y está marcado como pensionado
		if (usuario != null && usuario.getEdoUsu() != null && usuario.getEdoUsu() == 1L) {
			if (usuario.getContraseña() != null && usuario.getContraseña().equals(contraseña)) {
				registryService.setRegistry4Pension(usuario.getTokenEst());
				return true;
			}
		}

		return false;
	}

	// Pension Capabilities
	@Override
	public List<String> getTablaPLacas(int token) {

		Usuario usr = usudao.findUsuarioByToken(token);
		if (usr != null) {
			List<String> placas = usr.getPlacas();
			return placas;
		} else {
			return Collections.emptyList();
		}
	}

	/* Admin user Path */
	// creamos un usuario con privilegios de administrador
	@Override
	public Usuario createUsuarioAdmin(String correo, String contraseña, String nombre) {
		Boolean existUsuario = usudao.existUsuarioByCorreo(correo);
		if (existUsuario) {
			return null;
		}

		if (correo != null && contraseña != null && nombre != null) {
			Usuario usuario = new Usuario(null, 2L, correo, contraseña, null, 0, nombre);
			usudao.createOrUpdateUsuario(usuario);
			return usuario;
		}
		return null;
	}

	// verificar si el usuario admin existe y su token es por defecto 0
	@Override
	public Boolean existUsuarioAdministador(String correo, String contraseña) {

		Usuario usuario = usudao.findUsuarioByCorreo(correo);
		if (usuario != null && usuario.getEdoUsu() != null && usuario.getEdoUsu() == 2L && usuario.getTokenEst() == 0) {
			if (usuario.getContraseña() != null && usuario.getContraseña().equals(contraseña)) {
				registryService.setRegistry5Admin(usuario.getTokenEst());
				return true;
			}
		}

		return false;
	}

	// Si existe muestra el lobby
	/* Loby user cappabilities */
	// Ver Placas de los usuarios para actualizar

	@Override
	public void deleteUsuarioByCveUsu(int token) {

		List<Usuario> usuariosToDelete = usudao.findUsuariosByToken(token);

		for (Usuario usuario : usuariosToDelete) {
			if (usuario.getEdoUsu() == 0L) {
				usudao.deleteUsuarioByCveUsu(usuario.getCveUsu());
			}
		}
	}

	@Override
	public void updateTableUsr(int token, String placa) {
		Usuario usuario = usudao.findUsuarioByToken(token);
		if (usuario != null && !usuario.getPlacas().contains(placa)) {
			usuario.getPlacas().add(placa);
			usudao.createOrUpdateUsuario(usuario);
		}
	}

	@Override
	public List<Usuario> getAllUsu() {
		return usudao.getAllUsu();
	}

	@Override
	public boolean updateTableUsr(String correo, String pswd, List<String> placas, String nombre, int token) {
		Usuario usuario = usudao.findUsuarioByToken(token);
		usuario.setContraseña(pswd);
		usuario.setCorreo(correo);
		usuario.setPlacas(placas);
		usuario.setNomUsu(nombre);

		usudao.createOrUpdateUsuario(usuario);

		return true;
	}

	// csv est
	@Override
	public ByteArrayResource createCsvEst() {
		List<Estacionamiento> estacionamientos = estDao.findAllEstacionamientos();

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(baos), CSVFormat.DEFAULT
						.withHeader("Token Ingreso", "Estado de Pago", "Total", "Clave Estacionamiento",
								"Fecha de Ingreso", "Fecha de Salida"))) {

			BigDecimal ingresosTotales = BigDecimal.ZERO;

			for (Estacionamiento estacionamiento : estacionamientos) {
				BigDecimal total = estacionamiento.getTotal() != null ? new BigDecimal(estacionamiento.getTotal())
						: BigDecimal.ZERO;

				csvPrinter.printRecord(
						estacionamiento.getTokenIngreso(),
						estacionamiento.getEdoPago(),
						total,
						estacionamiento.getCveEst(),
						estacionamiento.getIngresoFec(),
						estacionamiento.getSalidaFec());

				ingresosTotales = ingresosTotales.add(total);
			}

			csvPrinter.printRecord("Ingresos Totales", "", ingresosTotales, "", "", "");

			csvPrinter.flush();

			return new ByteArrayResource(baos.toByteArray());

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
