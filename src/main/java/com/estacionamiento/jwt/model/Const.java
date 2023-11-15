package com.estacionamiento.jwt.model;

import java.util.Random;

public class Const {
    public final double costoPorHora = 81.50;
    /*public final double costoPorMinuto = 1.35;*/

    /*id publica */
    public String idPublicaGenerator(){
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 

        Random random = new Random();
        char letra = letras.charAt(random.nextInt(letras.length()));
        
        // Genera 5 números aleatorios
        int numero = random.nextInt(90000) + 10000; // Para asegurarte de obtener un número de 5 dígitos
        
        // Concatena la letra y los números aleatorios para formar el ID
        String id = letra + String.valueOf(numero);

        return id;
    }
}
