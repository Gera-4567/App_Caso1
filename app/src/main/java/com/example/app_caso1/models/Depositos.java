package com.example.app_caso1.models;

public class Depositos {

    String $id;
    int id_deposito;
    String nom_deposito;
    float capacidad;
    String uso;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public int getId_deposito() {
        return id_deposito;
    }

    public void setId_deposito(int id_deposito) {
        this.id_deposito = id_deposito;
    }

    public String getNom_deposito() {
        return nom_deposito;
    }

    public void setNom_deposito(String nom_deposito) {
        this.nom_deposito = nom_deposito;
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
