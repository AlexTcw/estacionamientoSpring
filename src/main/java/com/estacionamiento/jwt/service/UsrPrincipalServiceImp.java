package com.estacionamiento.jwt.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.dao.UsrPrincipalDao.UsrPrincipalDao;
import com.estacionamiento.jwt.model.UsrPrincipal;

@Service
public class UsrPrincipalServiceImp implements UsrPrincipalService {
	
	@Autowired
	UsrPrincipalDao usrPrincipalDao;
	
	@Override
	public UsrPrincipal SavePrincipal(UsrPrincipal usrPrincipal) {
		return usrPrincipalDao.saveorUpdateUPD(usrPrincipal);
	}
	
	
	@Override
	public String GeneradorTokens(String token) {
		
		String tokenValue;
		Long id = usrPrincipalDao.maxId();
		
		if (token == null) {
			tokenValue = UUID.randomUUID().toString();
		} else {
			tokenValue = token;
		}
		
		if (id == null) {
			id =0L;
		}
		
	    UsrPrincipal usr = new UsrPrincipal();
	    usr.setTokenIngreso(tokenValue);
	    
	    // Obtener fecha y hora actuales
	    LocalDateTime currentDateTime = LocalDateTime.now();
	    usr.setIngreso(currentDateTime);
	    
	    usr.setIdPublica("User00" + id);
	    usrPrincipalDao.saveorUpdateUPD(usr);

		
		return "ok";
	}

}
