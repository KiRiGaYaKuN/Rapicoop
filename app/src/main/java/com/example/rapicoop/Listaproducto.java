package com.example.rapicoop;

import android.graphics.Bitmap;

public class Listaproducto {

        private Bitmap img;
        public String nproducto;
        public String precio;
        public String descripcion;
        public String cantidad;

        public Listaproducto(Bitmap img, String nproducto, String precio, String descripcion, String cantidad) {
            this.img = img;
            this.nproducto = nproducto;
            this.precio = precio;
            this.descripcion = descripcion;
            this.cantidad = cantidad;
        }

        public String getnproducto() {
            return nproducto;
        }

        public void setnproducto(String nproducto) {
            this.nproducto = nproducto;
        }

        public String getprecio() {
            return precio;
        }

        public void setprecio(String precio) {
            this.precio = precio;
        }

        public String getdescripcion() {
            return descripcion;
        }

        public void setdescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public Bitmap getImg() {
            return img;
        }

        public void setImg(Bitmap img) {
            this.img = img;
        }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
