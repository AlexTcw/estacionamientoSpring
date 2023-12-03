package com.estacionamiento.jwt.service.Historial;

import java.util.List;

import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.model.Historial;

@Service
public interface HistorialService {
    List<Historial> findAllHistorials();

    public Boolean setEstacionamientoDataIntoHistorial(int token);
}
