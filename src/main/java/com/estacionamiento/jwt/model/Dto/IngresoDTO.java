package com.estacionamiento.jwt.model.Dto;

import java.time.LocalDateTime;

public class IngresoDTO {

    String idPublica;
    LocalDateTime ingreso;
    String mensaje;
    
    public String getIdPublica() {
        return idPublica;
    }
    public void setIdPublica(String idPublica) {
        this.idPublica = idPublica;
    }
    public LocalDateTime getIngreso() {
        return ingreso;
    }
    public void setIngreso(LocalDateTime ingreso) {
        this.ingreso = ingreso;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    
    
}
