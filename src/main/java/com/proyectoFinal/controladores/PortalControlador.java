package com.proyectoFinal.controladores;

import com.proyectoFinal.entidades.Curso;
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
    
    private final int COURSES_PER_VIEW = 3;

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

    @GetMapping("/login-profesor")
    public String loginProfesor(ModelMap modelo, @RequestParam(required = false) String error, @RequestParam(required = false) String logout) {

        if (error != null) {
            modelo.put("error", "Usuario o Clave incorrectos dsadsadad: de profesor(");
        }

        if (logout != null) {
            modelo.put("logout", "Has cerrado sesión exitosamente :)");
        }

        return "login-profesor.html";
    }

    @GetMapping("/login-alumno")
    public String loginAlumno(ModelMap modelo, @RequestParam(required = false) String error, @RequestParam(required = false) String logout) {

        if (error != null) {
            modelo.put("error", "Usuario o Clave incorrectos :(");
        }

        if (logout != null) {
            modelo.put("logout", "Has cerrado sesión exitosamente :)");
        }

        return "login-alumno.html";
    }

    @GetMapping("/nivel-cursos")
    public String mostrarXnivel(ModelMap modelo) {
        try {
            List<Curso> cursos = cursoServicio.listarCursos();

            List<List<Curso>> cursosGroupedBy3 = new ArrayList<>();

            for (int i = 0; i < cursos.size(); i += COURSES_PER_VIEW) {
                List<Curso> courses = new ArrayList();
                for (int j = i; j < COURSES_PER_VIEW + i; j++) {
                    if (j == cursos.size()) break;
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
        return "nivel-cursos";
    }

    @GetMapping("/info-idiomas")
    public String infoIdiomas() {
        return "idiomas.html";
    }

//    @GetMapping("/login/{id}")
//    public String loginProfesor(@PathVariable String id, ModelMap modelo, @RequestParam(required = false) String error, @RequestParam(required = false) String logout) throws Exception {
//
//        Usuario u = usurioServicio.BuscarId(id);
//        if (u.getRol().toString().equals("ROLE_PROFESOR") ) {
//            if (error != null) {
//                modelo.put("error", "Usuario o Clave incorrectos :(");
//            }
//
//            if (logout != null) {
//                modelo.put("logout", "Has cerrado sesión exitosamente :)");
//            }
//            return "login-profesor.html";
//        } else {
//            return "login-alumno.html";
//        }
//        
//
//    }
}
