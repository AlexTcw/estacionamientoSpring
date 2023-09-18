package com.estacionamiento.jwt.model.Dto;

import java.text.DecimalFormat;

public class ReciboDTO {

    String idPublica;
    double totalHoras;
    double totalMinutos;
    double totalCosto;
    String mensaje;

    public String getIdPublica() {
        return idPublica;
    }

    public void setIdPublica(String idPublica) {
        this.idPublica = idPublica;
    }

    public double getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(double totalHoras) {
        this.totalHoras = totalHoras;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public double getTotalMinutos() {
        return totalMinutos;
    }

    public void setTotalMinutos(double totalMinutos) {
        this.totalMinutos = totalMinutos;
    }

    public double getTotalCosto() {
        return totalCosto;
    }

    public void setTotalCosto(double totalCosto) {
        DecimalFormat df = new DecimalFormat("#.##");
        this.totalCosto = Double.parseDouble(df.format(totalCosto));
    }

}
