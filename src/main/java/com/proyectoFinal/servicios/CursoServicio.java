package com.proyectoFinal.servicios;

import com.proyectoFinal.entidades.Curso;
import com.proyectoFinal.entidades.Usuario;
import com.proyectoFinal.enums.Idioma;
import com.proyectoFinal.enums.Nivel;
import com.proyectoFinal.enums.Rol;
import com.proyectoFinal.repositorios.CursoRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoServicio {

    @Autowired
    private CursoRepositorio cursoRepositorio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    public Curso crear(String nombre, Nivel nivel, Idioma idioma, String idProfesor) throws Exception {

        validar(nombre, nivel, idioma, idProfesor);

        Curso curso = new Curso();

        curso.setNombre(nombre);
        curso.setNivel(nivel);
        curso.setIdioma(idioma);
        curso.setAlumnos(usuarioServicio.buscarAlumnos(Rol.ALUMNO));

        Usuario profesor = usuarioServicio.buscarProfesor(idProfesor, Rol.PROFESOR);
        if (profesor != null) {
            curso.setProfesor(profesor);
        } else {
            throw new Exception("No se pudo encontrar el profesor solicitado");
        }
        
        curso.setAlta(new Date());
        curso.setBaja(null);

        return cursoRepositorio.save(curso);
    }

    public Curso modificar(String id, String nombre, Nivel nivel, Idioma idioma, String idProfesor) throws Exception {

        validar(nombre, nivel, idioma, idProfesor);

        Optional<Curso> respuesta = cursoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Curso curso = respuesta.get();

            curso.setNombre(nombre);
            curso.setNivel(nivel);
            curso.setIdioma(idioma);

            Usuario profesor = usuarioServicio.buscarProfesor(idProfesor, Rol.PROFESOR);
            if (profesor != null) {
                curso.setProfesor(profesor);
            } else {
                throw new Exception("No se pudo encontrar el profesor solicitado");
            }
            
            return cursoRepositorio.save(curso);
        } else {
            throw new Exception("No se pudo encontrar el curso solicitado");
        }
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Curso deshabilitar(String id) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            throw new Exception("El ID no puede ser nulo");
        }
        
        Optional<Curso> respuesta = cursoRepositorio.findById(id);
        if(respuesta.isPresent()) {
            Curso curso = respuesta.get();
            curso.setBaja(new Date());
            return cursoRepositorio.save(curso);
        } else {
            throw new Exception("No se pudo encontrar el libro solicitado");
        }
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Curso habilitar(String id) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            throw new Exception("El ID no puede ser nulo");
        }
        
        Optional<Curso> respuesta = cursoRepositorio.findById(id);
        if(respuesta.isPresent()) {
            Curso curso = respuesta.get();
            curso.setBaja(null);
            return cursoRepositorio.save(curso);
        } else {
            throw new Exception("No se pudo encontrar el libro solicitado");
        }
    }
    
    @Transactional(readOnly = true)
    public List<Curso> listarUsuarios() {
        return cursoRepositorio.findAll();
    }

    private void validar(String nombre, Nivel nivel, Idioma idioma, String idProfesor) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("Debe ingresar su nombre");
        }
        if (idProfesor == null || idProfesor.trim().isEmpty()) {
            throw new Exception("El ID no puede ser nulo");
        }
        if (nivel == null || nivel.toString().trim().isEmpty()) {
            throw new Exception("Debe indicar el nivel del curso");
        }
        if (idioma == null || idioma.toString().trim().isEmpty()) {
            throw new Exception("Debe indicar el idioma del curso");
        }
    }
}
