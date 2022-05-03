package com.proyectoFinal.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Foto implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String nime;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;

    public Foto() {
    }

    public Foto(String id, String nombre, String nime, byte[] contenido) {
        this.id = id;
        this.nombre = nombre;
        this.nime = nime;
        this.contenido = contenido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNime() {
        return nime;
    }

    public void setNime(String nime) {
        this.nime = nime;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    
    @Override
    public String toString() {
        return "Foto{" + "id=" + id + ", nombre=" + nombre + ", nime=" + nime + ", contenido=" + contenido + '}';
    }
           
    
    
}
