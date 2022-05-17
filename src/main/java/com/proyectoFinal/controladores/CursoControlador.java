package com.proyectoFinal.controladores;

import com.proyectoFinal.entidades.Curso;
import com.proyectoFinal.entidades.Usuario;
import com.proyectoFinal.enums.Idioma;
import com.proyectoFinal.enums.Nivel;
import com.proyectoFinal.enums.Rol;
import com.proyectoFinal.enums.Turno;
import com.proyectoFinal.servicios.CursoServicio;
import com.proyectoFinal.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String guardar(ModelMap model, @RequestParam String nombre, @RequestParam Nivel nivel, @RequestParam Idioma idioma, @RequestParam String idProfesor) {

        try {
            cursoServicio.crear(nombre, nivel, idioma, idProfesor);

            return "redirect:/curso/list-curso";
        } catch (Exception e) {

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

    @GetMapping("/update/{id}")
    public String update(@PathVariable String id, ModelMap modelo) throws Exception {
        Curso c = cursoServicio.BuscarId(id);
        modelo.put("curso", c);
        return "index";
    }

    @GetMapping("/turno-cursos")
    public String mostrarXturno(ModelMap modelo, @RequestParam Turno turno) throws Exception {
        try {
            List<Curso> listaCurso = cursoServicio.listarXturno(turno);
            modelo.put("cursoXturno", listaCurso);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "index";
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

    @PostMapping("/update")
    public String updatePost(ModelMap modelo, @RequestParam String id, @RequestParam String nombre, @RequestParam Nivel nivel, @RequestParam Idioma idioma, @RequestParam String idProfesor) {
        try {
            cursoServicio.modificar(id, nombre, nivel, idioma, idProfesor);
            modelo.put("exito", "se pudo actualizar");
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "index";
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }
}
