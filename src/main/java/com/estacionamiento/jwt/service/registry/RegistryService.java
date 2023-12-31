package com.estacionamiento.jwt.service.registry;

import org.springframework.stereotype.Service;

@Service
public interface RegistryService {

	int getLastEdoRegistry();

	Boolean setRegistry1Enrroll(int token);

	Boolean setRegistry2proccess();

	Boolean setRegistry3Exit(int token);

	int getLastTokenRegistry();

	Boolean setRegistryEdo(int token, int edo);

	public Boolean setRegistry4Pension(int token);

	public Boolean setRegistry5Admin(int token);

}
