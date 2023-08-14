package com.estacionamiento.jwt.service;

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

}
