package com.proyectoFinal.controladores;

import aj.org.objectweb.asm.Attribute;
import com.proyectoFinal.entidades.Curso;
import com.proyectoFinal.entidades.Usuario;
import com.proyectoFinal.enums.Idioma;
import com.proyectoFinal.enums.Nivel;
import com.proyectoFinal.enums.Turno;
import com.proyectoFinal.servicios.CursoServicio;
import com.proyectoFinal.servicios.UsuarioServicio;
import java.util.ArrayList;
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
    
    @GetMapping("/list-curso-alumno/{id}")
    public String buscarCursoDeAlumno(ModelMap model, @PathVariable String id) {

        Curso cursos = cursoServicio.bucarCursoDeAlumno(id);

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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/form-curso")
    public String formulario(ModelMap model) {

        List<Usuario> profesores = usuarioServicio.listarProfesores();

        model.addAttribute("profesores", profesores);

        List<Usuario> alumnos = usuarioServicio.buscarAlumnos();

        model.addAttribute("alumnos", alumnos);

        return "form-curso";
    }

    @PostMapping("/form-curso")
    public String guardar(RedirectAttributes attr, ModelMap model, @RequestParam MultipartFile archivo, @RequestParam String nombre, @RequestParam Nivel nivel, @RequestParam Turno turno, @RequestParam Idioma idioma, @RequestParam String idProfesor) {

        try {
            cursoServicio.crear(archivo, nombre, nivel, idioma, turno, idProfesor);
            attr.addFlashAttribute("exito", "se registró el curso correctamente");

            return "redirect:/curso/form-curso";
        } catch (Exception e) {

            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/curso/form-curso";

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

        List<Usuario> u = usuarioServicio.listarProfesores();

        modelo.put("curso", c);
        modelo.put("profesores", u);
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
    public String añadirAlumno(RedirectAttributes attr, ModelMap modelo, @PathVariable String id, HttpSession session) {

        try {
            Usuario login = (Usuario) session.getAttribute("usuariosession");

            System.out.println("id de curso " + id + "   id usuario " + login.getId());
            cursoServicio.añadirAlumno(id, login.getId());
            attr.addFlashAttribute("exito", "se ha inscripto con éxito al curso");
            
            return "redirect:/campus-alumno";

        } catch (Exception ex) {
            Logger.getLogger(CursoControlador.class.getName()).log(Level.SEVERE, null, ex);
            attr.addFlashAttribute("error", "usted ya está inscripto en algun curso");
            return "redirect:/campus-alumno";
        }
    }

    @PostMapping("/actualizar-curso")
    public String editar(RedirectAttributes attr, ModelMap modelo, MultipartFile archivo, @RequestParam String id, @RequestParam String nombre, @RequestParam Nivel nivel, @RequestParam Idioma idioma, @RequestParam Turno turno, @RequestParam(required = false) String idProfesor) {
        try {
            cursoServicio.modificar(archivo, id, nombre, nivel, idioma, turno, idProfesor);
            attr.addFlashAttribute("exito", "se pudo actualizar con exito");
            return "redirect:/curso/editar-curso/"+id;
        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/curso/editar-curso/"+id;
        }

    }
    
    @GetMapping("mis-cursos/{id}")
    public String misCursos(ModelMap modelo, @PathVariable String id, HttpSession session) throws Exception {

        List<Curso> cursos = cursoServicio.listarCursosPorProfesor(id);

        modelo.addAttribute("cursos", cursos);

        return "list-curso";

    }

    @GetMapping("index")
    public String index() {
        return "index";
    }
}
