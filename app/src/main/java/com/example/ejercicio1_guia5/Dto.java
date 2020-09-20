package com.example.ejercicio1_guia5;

import android.content.Context;
import java.io.Serializable;

public class Dto {
    int code;
    String des;
    double precio;

    public Dto() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
