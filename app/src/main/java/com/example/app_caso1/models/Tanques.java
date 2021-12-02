package com.example.app_caso1.models;

public class Tanques {

    String $id;
    int id_tanque;
    String nom_tanque;
    float capacidad;
    String uso;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public int getId_tanque() {
        return id_tanque;
    }

    public void setId_tanque(int id_tanque) {
        this.id_tanque = id_tanque;
    }

    public String getNom_tanque() {
        return nom_tanque;
    }

    public void setNom_tanque(String nom_tanque) {
        this.nom_tanque = nom_tanque;
    }

    public float getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(float capacidad) {
        this.capacidad = capacidad;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }
}
