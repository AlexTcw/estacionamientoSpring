package com.estacionamiento.jwt.model.DTO;

import java.util.List;

public class UsrInfoDto {

    String nombreUsu;
    String correoUsu;
    List<String> placas;
    int token;

    public String getNombreUsu() {
        return nombreUsu;
    }

    public void setNombreUsu(String nombreUsu) {
        this.nombreUsu = nombreUsu;
    }

    public String getCorreoUsu() {
        return correoUsu;
    }

    public void setCorreoUsu(String correoUsu) {
        this.correoUsu = correoUsu;
    }

    public List<String> getPlacas() {
        return placas;
    }

    public void setPlacas(List<String> placas) {
        this.placas = placas;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

}
