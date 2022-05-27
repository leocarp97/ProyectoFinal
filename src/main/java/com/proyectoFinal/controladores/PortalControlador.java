package com.proyectoFinal.controladores;

import com.proyectoFinal.entidades.Curso;
import com.proyectoFinal.entidades.Usuario;
import com.proyectoFinal.repositorios.UsuarioRepositorio;
import com.proyectoFinal.servicios.CursoServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    CursoServicio cursoServicio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    private final int COURSES_PER_VIEW = 2;

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }
    
//    @GetMapping("/campus-alumno")
//    public String campusAlumno() {
//        return "campus-alumno.html";
//    }

    @GetMapping("/login")
    public String login(ModelMap modelo, @RequestParam(required = false) String error, @RequestParam(required = false) String logout) throws Exception {
        
        if (error != null) {
            modelo.put("error", "Usuario o Clave incorrectos");
        }

        if (logout != null) {
            modelo.put("logout", "Has cerrado sesión exitosamente :)");
        }

        return "login.html";
    }

    @GetMapping("/campus-alumno")
    public String mostrarXnivel(ModelMap modelo
    ) {
        try {
            List<Curso> cursos = cursoServicio.listarCursos();

            List<List<Curso>> cursosGroupedBy3 = new ArrayList<>();

            for (int i = 0; i < cursos.size(); i += COURSES_PER_VIEW) {
                List<Curso> courses = new ArrayList();
                for (int j = i; j < COURSES_PER_VIEW + i; j++) {
                    if (j == cursos.size()) {
                        break;
                    }
                    Curso curso = cursos.get(j);
                    courses.add(curso);
                }
                cursosGroupedBy3.add(courses);
            }

            modelo.put("cursosBy3", cursosGroupedBy3);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            modelo.put("error", e.getMessage());
        }
        return "campus-alumno.html";
    }

    @GetMapping("/info-idiomas")
    public String infoIdiomas() {
        return "idiomas.html";
    }

     
}
