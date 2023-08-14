package com.estacionamiento.jwt.dao.UsrPrincipalDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estacionamiento.jwt.model.UsrPrincipal;
import com.estacionamiento.jwt.repository.UsrPrincipalRepository;

@Repository
public class UsrPrincipalDaoImp implements UsrPrincipalDao{
	
	@Autowired
	UsrPrincipalRepository repository;
	
	@Override
	public UsrPrincipal saveorUpdateUPD(UsrPrincipal usrPrincipal) {
		return repository.save(usrPrincipal);
	}
	
	@Override
	public void deleteUPD(Long id) {
		 repository.deleteById(id);
	}
		
}
