package com.proyectoFinal.controladores;

import aj.org.objectweb.asm.Attribute;
import com.proyectoFinal.entidades.Curso;
import com.proyectoFinal.entidades.Usuario;
import com.proyectoFinal.enums.Idioma;
import com.proyectoFinal.enums.Nivel;
import com.proyectoFinal.enums.Turno;
import com.proyectoFinal.servicios.CursoServicio;
import com.proyectoFinal.servicios.UsuarioServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/curso")
public class CursoControlador {

    @Autowired
    CursoServicio cursoServicio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/list-curso")
    public String listarCursos(ModelMap model) {

        List<Curso> cursos = cursoServicio.listarCursos();

        model.addAttribute("cursos", cursos);

        return "list-curso";
    }


    @GetMapping("/list-alumnos/{id}")
    public String listarAlumnos(ModelMap model, @PathVariable String id) {

        List<Usuario> alumnos = cursoServicio.listarAlumnos(id);

        model.addAttribute("alumnos", alumnos);

        return "list-alumnos";
    }

    @GetMapping("/list-curso-activos")
    public String listarCursosActivos(ModelMap model) {

        List<Curso> cursos = cursoServicio.buscarCursosActivos();

        model.addAttribute("cursos", cursos);

        return "list-curso";
    }

    @GetMapping("/form-curso")
    public String formulario(ModelMap model) {

        List<Usuario> profesores = usuarioServicio.listarProfesores();

        model.addAttribute("profesores", profesores);

        List<Usuario> alumnos = usuarioServicio.buscarAlumnos();

        model.addAttribute("alumnos", alumnos);

        return "form-curso";
    }

    @PostMapping("/form-curso")
    public String guardar(ModelMap model, @RequestParam MultipartFile archivo, @RequestParam String nombre, @RequestParam Nivel nivel, @RequestParam Turno turno, @RequestParam Idioma idioma, @RequestParam String idProfesor) {

        try {
            cursoServicio.crear(archivo, nombre, nivel, idioma, turno, idProfesor);

            return "redirect:/curso/list-curso";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "list-curso";
        }
    }

    @GetMapping("/eliminar-curso/{id}")
    public String eliminar(@PathVariable String id) throws Exception {

        cursoServicio.deshabilitar(id);

        return "redirect:/curso/list-curso/";

    }

    @GetMapping("/habilitar-curso/{id}")
    public String habilitar(@PathVariable String id) throws Exception {

        cursoServicio.habilitar(id);

        return "redirect:/curso/list-curso/";

    }

    @GetMapping("/editar-curso/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) throws Exception {
        Curso c = cursoServicio.BuscarId(id);
        modelo.put("curso", c);
        return "editar-curso";
    }

    @GetMapping("/turno-cursos")
    public String mostrarXturno(RedirectAttributes attr, ModelMap modelo, @RequestParam Turno turno) throws Exception {
        try {
            List<Curso> listaCurso = cursoServicio.listarXturno(turno);

            attr.addAttribute("cursoXturno", listaCurso);

        } catch (Exception e) {
            attr.addAttribute("error", e.getMessage());
        }
        return "redirect:/curso/list-cursos";
    }

    @GetMapping("/nivel-cursos/{id}")
    public String añadirAlumno(ModelMap modelo, @PathVariable String id, HttpSession session) {

        try {
            Usuario login = (Usuario) session.getAttribute("usuariosession");

            System.out.println("id de curso " + id + "   id usuario " + login.getId());
            cursoServicio.añadirAlumno(id, login.getId());

       

        } catch (Exception ex) {
            Logger.getLogger(CursoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/";
    }

//    @GetMapping("/nivel-cursos")
//    public String mostrarXnivel(ModelMap modelo) {
//        try {
//            List<Curso> cursos = cursoServicio.listarCursos();
//            modelo.put("cursos", cursos);
//
//        } catch (Exception e) {
//            modelo.put("error", e.getMessage());
//        }
//        return "nivel-cursos";
//    }
    @PostMapping("/actualizar-curso")
    public String editar(ModelMap modelo, @RequestParam(required = false) MultipartFile archivo, @RequestParam String id, @RequestParam String nombre, @RequestParam Nivel nivel, @RequestParam Idioma idioma, @RequestParam Turno turno, @RequestParam(required = false) String idProfesor) {
        try {
            cursoServicio.modificar(archivo, id, nombre, nivel, idioma, turno, idProfesor);
            modelo.put("exito", "se pudo actualizar");
            return "redirect:/curso/list-curso/";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/curso/form-curso/";
        }

    }

    @GetMapping("index")
    public String index() {
        return "index";
    }
}
