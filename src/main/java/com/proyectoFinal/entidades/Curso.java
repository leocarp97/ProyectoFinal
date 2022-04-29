package com.proyectoFinal.entidades;

import com.proyectoFinal.enums.Idioma;
import com.proyectoFinal.enums.Nivel;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Cursos")
public class Curso implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "nombre_del_curso")
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_del_curso")
    private Nivel nivel;
    
    @OneToMany
    @JoinColumn(name = "alumnos")
    private List<Usuario> alumnos;
    
    @OneToOne
    @JoinColumn(name = "profesor")
    private Usuario profesor;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "idioma_del_curso")
    private Idioma idioma;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;

    public Curso(String id, String nombre, Nivel nivel, List<Usuario> alumnos, Usuario profesor, Idioma idioma, Date alta, Date baja) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
        this.alumnos = alumnos;
        this.profesor = profesor;
        this.idioma = idioma;
        this.alta = alta;
        this.baja = baja;
    }

    public Curso() {
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

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public List<Usuario> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Usuario> alumnos) {
        this.alumnos = alumnos;
    }

    public Usuario getProfesor() {
        return profesor;
    }

    public void setProfesor(Usuario profesor) {
        this.profesor = profesor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Date getBaja() {
        return baja;
    }

    public void setBaja(Date baja) {
        this.baja = baja;
    }

    @Override
    public String toString() {
        return "Curso{" + "id=" + id + ", nombre=" + nombre + ", nivel=" + nivel + ", alumnos=" + alumnos + ", profesor=" + profesor + ", idioma=" + idioma + ", alta=" + alta + ", baja=" + baja + '}';
    }
    
}
