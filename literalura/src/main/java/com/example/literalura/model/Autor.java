package com.example.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer añoNacimiento;

    private Integer añoFallecimiento;

    public Autor() {}

    public Autor(String nombre, Integer añoNacimiento, Integer añoFallecimiento) {
        this.nombre = nombre;
        this.añoNacimiento = añoNacimiento;
        this.añoFallecimiento = añoFallecimiento;
    }

    // Getters y Setters
    public Long getId() { return id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getAñoNacimiento() { return añoNacimiento; }

    public void setAñoNacimiento(Integer añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }

    public Integer getAñoFallecimiento() {
        return añoFallecimiento;
    }

    public void setAñoFallecimiento(Integer añoFallecimiento) {
        this.añoFallecimiento = añoFallecimiento;
    }
}