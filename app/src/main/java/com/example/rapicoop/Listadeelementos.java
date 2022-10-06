package com.example.rapicoop;

import android.graphics.Bitmap;

public class Listadeelementos {

    private Bitmap img;
    public String nombrerestaurante;
    public String horario;
    public String estado;
    public String vendedor;

    public Listadeelementos(Bitmap img, String nombrerestaurante, String horario, String estado, String vendedor) {
        this.setImg(img);
        this.nombrerestaurante = nombrerestaurante;
        this.horario = horario;
        this.estado = estado;
        this.vendedor = vendedor;
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

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }
}

