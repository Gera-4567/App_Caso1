package com.example.app_caso1.models;

public class Usuarios {

    String $id;
    int id_usuario;
    String nom_usuario;
    String pass_usuario;
    String Ap1_usuario;
    String Ap2_usuario;
    String email;

    public Usuarios(String nom_usuario, String pass_usuario, String Ap1_usuario, String Ap2_usuario, String email) {
        this.nom_usuario = nom_usuario;
        this.pass_usuario = pass_usuario;
        this.Ap1_usuario = Ap1_usuario;
        this.Ap2_usuario = Ap2_usuario;
        this.email = email;
    }

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNom_usuario() {
        return nom_usuario;
    }

    public void setNom_usuario(String nom_usuario) {
        this.nom_usuario = nom_usuario;
    }

    public String getPass_usuario() {
        return pass_usuario;
    }

    public void setPass_usuario(String pass_usuario) {
        this.pass_usuario = pass_usuario;
    }

    public String getAp1_usuario() {
        return Ap1_usuario;
    }

    public void setAp1_usuario(String ap1_usuario) {
        Ap1_usuario = ap1_usuario;
    }

    public String getAp2_usuario() {
        return Ap2_usuario;
    }

    public void setAp2_usuario(String ap2_usuario) {
        Ap2_usuario = ap2_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
