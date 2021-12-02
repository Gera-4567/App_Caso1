package com.example.app_caso1.models;

public class Presas {

    String $id;
    int id_presa;
    String nom_presa;
    float capacidad;
    String uso;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public int getId_presa() {
        return id_presa;
    }

    public void setId_presa(int id_presa) {
        this.id_presa = id_presa;
    }

    public String getNom_presa() {
        return nom_presa;
    }

    public void setNom_presa(String nom_presa) {
        this.nom_presa = nom_presa;
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
