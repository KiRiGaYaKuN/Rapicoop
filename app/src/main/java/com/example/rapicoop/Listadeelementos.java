package com.example.rapicoop;

import android.graphics.Bitmap;

public class Listadeelementos {

    private Bitmap img;
    public String nombrerestaurante;
    public String horario;
    public String estado;

    public Listadeelementos(Bitmap img, String nombrerestaurante, String horario, String estado) {
        this.setImg(img);
        this.nombrerestaurante = nombrerestaurante;
        this.horario = horario;
        this.estado = estado;
    }



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

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}

