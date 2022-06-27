package com.proyectoFinal.controladores;

import com.proyectoFinal.entidades.Curso;
import com.proyectoFinal.entidades.Usuario;
import com.proyectoFinal.enums.Pais;
import com.proyectoFinal.enums.Rol;
import com.proyectoFinal.repositorios.UsuarioRepositorio;
import com.proyectoFinal.servicios.CursoServicio;
import com.proyectoFinal.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    CursoServicio cursoServicio;
    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    private final int COURSES_PER_VIEW = 2;

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

    @PostMapping("/index")
    public String guardar(RedirectAttributes attr, ModelMap model, @RequestParam(required = false) MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam(required = false) Integer dni, @RequestParam String email, @RequestParam Integer telefono, @RequestParam String password, @RequestParam String region, @RequestParam Pais pais, @RequestParam (required = false) Rol rol) {

        try {
            usuarioServicio.registrar(archivo, nombre, apellido, dni, email, telefono, password, region, pais, Rol.ALUMNO);
            attr.addFlashAttribute("exito", "usted se ha registrado exitosamente");
            return "redirect:/";
        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            model.put("error", e.getMessage());
            return "redirect:/";
        }
    }

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
    
    @PreAuthorize("hasAnyRole('ROLE_ALUMNO')")
    @GetMapping("/campus-alumno")
    public String campusAlumno(ModelMap modelo) {
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
    
    @PreAuthorize("hasAnyRole('ROLE_PROFESOR')")
    @GetMapping("/campus-profesor")
    public String campusProfesor(ModelMap modelo) {
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
        return "campus-profesor.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/campus-admin")
    public String campusAdmin(ModelMap modelo) {
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
        return "campus-admin.html";
    }

    @GetMapping("/info-idiomas")
    public String infoIdiomas() {
        return "idiomas.html";
    }

}
