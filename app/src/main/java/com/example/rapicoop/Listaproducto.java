package com.example.rapicoop;

import android.graphics.Bitmap;

public class Listaproducto {

        private Bitmap img;
        public String nproducto;
        public String precio;
        public String descripcion;

        public Listaproducto(String img, String nproducto, String precio, String descripcion) {
            //this.setImg(img);
            this.nproducto = nproducto;
            this.precio = precio;
            this.descripcion = descripcion;
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
}
