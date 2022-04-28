
package com.proyectoFinal.entidades;

import com.proyectoFinal.enums.Idioma;
import com.proyectoFinal.enums.Nivel;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Cursos")
public class Curso implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @Column(name = "nombre_del_curso")
    private String nombre;
    
    @Column(name = "nivel_del_curso")
    @Enumerated(EnumType.STRING)
    private Nivel nivel;
    
    @OneToMany
    private List<Usuario> alumnos;
    
    @OneToOne
    private Usuario profesor;
    
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    

    

    public Curso(String id, String nombre, Nivel nivel, List<Usuario> alumnos, Usuario profesor, Idioma idioma) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
        this.alumnos = alumnos;
        this.profesor = profesor;
        this.idioma = idioma;
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

    @Override
    public String toString() {
        return "Curso{" + "id=" + id + ", nombre=" + nombre + ", nivel=" + nivel + ", alumnos=" + alumnos + ", profesor=" + profesor + ", idioma=" + idioma + '}';
    }
    
    

    
}
