package com.estacionamiento.jwt.service.Historial;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento.jwt.Dao.Estacionamiento.EstacionamientoDao;
import com.estacionamiento.jwt.Dao.Historial.HistorialDao;
import com.estacionamiento.jwt.model.Estacionamiento;
import com.estacionamiento.jwt.model.Historial;
import com.estacionamiento.jwt.service.Estacionamiento.EstacionamientoService;

@Service
public class HistorialServiceImp implements HistorialService {

    @Autowired
    HistorialDao historialDao;

    @Autowired
    EstacionamientoDao estacionamientoDao;

    @Autowired
    EstacionamientoService estacionamientoService;

    @Override
    public List<Historial> findAllHistorials() {
        return historialDao.findAllHistorials();
    }

    /* Generic User Path */
    // Migrate Estacionamiento Data into Historial
    @Override
    public Boolean setEstacionamientoDataIntoHistorial(int token) {
        Estacionamiento est = estacionamientoDao.findEstacionamientoByTokenIngreso(token);

        // Verificar si est es nulo
        if (est == null || est.getEdoPago() == false || est.getSalidaFec() == null || est.getTotal() == null) {
            return false;
        }

        Duration tiempoDeUso = estacionamientoService.calculateDuration(est.getIngresoFec(), est.getSalidaFec());
        Long horasDeUso = tiempoDeUso.toHours();

        Historial hst = new Historial();
        hst.setCveEst(est.getCveEst());
        hst.setIngresoFec(est.getIngresoFec());
        hst.setSalidaFec(est.getSalidaFec());

        // Verificar si el tiempo de uso es menor o igual a cero
        if (horasDeUso <= 0) {
            horasDeUso = 1L; // Establecer un valor predeterminado
        }

        hst.setTiempoDeUso(horasDeUso.doubleValue());
        hst.setTotal(est.getTotal());

        historialDao.saveOrUpdateHistorial(hst);

        return true;
    }

}
