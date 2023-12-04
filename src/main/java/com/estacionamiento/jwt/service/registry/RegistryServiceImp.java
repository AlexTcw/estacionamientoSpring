package com.estacionamiento.jwt.service.registry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.Dao.registry.RegistryDao;
import com.estacionamiento.jwt.model.RegistrosHuellas;

@Service
public class RegistryServiceImp implements RegistryService {

	@Autowired
	RegistryDao registryDao;

	@Override
	public int getLastEdoRegistry() {
		return registryDao.getLastEdoRegistry();
	}

	@Override
	public int getLastTokenRegistry() {
		return registryDao.getLastTokenFromRegistry();
	}

	@Override
	public Boolean setRegistry1Enrroll(int token) {
		RegistrosHuellas registro = new RegistrosHuellas();
		registro.setEdoRegistro(1);
		registro.setToken(token);
		registryDao.createNewEdo(registro);
		return true;
	}

	@Override
	public Boolean setRegistry2proccess() {
		RegistrosHuellas registro = new RegistrosHuellas();
		registro.setEdoRegistro(2);
		registryDao.createNewEdo(registro);
		return true;
	}

	@Override
	public Boolean setRegistry3Exit(int token) {
		RegistrosHuellas registro = new RegistrosHuellas();
		registro.setEdoRegistro(3);
		registro.setToken(token);
		registryDao.createNewEdo(registro);
		return true;
	}

	@Override
	public Boolean setRegistry4Pension(int token) {
		RegistrosHuellas registro = new RegistrosHuellas();
		registro.setEdoRegistro(4);
		registro.setToken(token);
		registryDao.createNewEdo(registro);
		return true;
	}

	@Override
	public Boolean setRegistry5Admin(int token) {
		RegistrosHuellas registro = new RegistrosHuellas();
		registro.setEdoRegistro(5);
		registro.setToken(token);
		registryDao.createNewEdo(registro);
		return true;
	}

	@Override
	public Boolean setRegistryEdo(int token, int edo) {
		RegistrosHuellas registro = new RegistrosHuellas();
		registro.setEdoRegistro(edo);
		registro.setToken(token);
		registryDao.createNewEdo(registro);
		return true;
	}

}
