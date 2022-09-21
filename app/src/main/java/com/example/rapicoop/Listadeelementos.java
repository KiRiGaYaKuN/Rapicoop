package com.example.rapicoop;

import androidx.annotation.NonNull;

public class Listadeelementos {

    //public String color;
    public String nombrerestaurante;
    public String horario;
    public String estado;

    public Listadeelementos(String color, String nombrerestaurante, String horario, String estado) {
        //this.color = color;
        this.nombrerestaurante = nombrerestaurante;
        this.horario = horario;
        this.estado = estado;
    }

    //public String getColor() {
        //return color;
    //}

    //public void setColor(String color) {
        //this.color = color;
    //}

    public String getNombrerestaurante() {
        return nombrerestaurante;
    }

    public void setNombrerestaurante(String nombrerestaurante) {
        this.nombrerestaurante = nombrerestaurante;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

