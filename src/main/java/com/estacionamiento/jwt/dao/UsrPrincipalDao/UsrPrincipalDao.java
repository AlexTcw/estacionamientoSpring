package com.estacionamiento.jwt.dao.UsrPrincipalDao;

import com.estacionamiento.jwt.model.UsrPrincipal;

public interface UsrPrincipalDao {

	UsrPrincipal saveorUpdateUPD(UsrPrincipal usrPrincipal);

	void deleteUPD(Long id);

	Long maxId();

	UsrPrincipal recuperarUsrPrincipalById(long id);

	UsrPrincipal recuperarUsrPrincipalByToken(String token);

}
