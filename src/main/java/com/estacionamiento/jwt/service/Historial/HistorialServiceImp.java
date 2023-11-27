package com.estacionamiento.jwt.service.Historial;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.Dao.Historial.HistorialDao;
import com.estacionamiento.jwt.model.Historial;

@Service
public class HistorialServiceImp implements HistorialService{
	
    @Autowired
    HistorialDao historialDao;

    @Override
    public List<Historial> findAllHistorials(){
        return historialDao.findAllHistorials();
    }

}
