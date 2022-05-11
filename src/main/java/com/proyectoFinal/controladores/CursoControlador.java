package com.proyectoFinal.controladores;

import com.proyectoFinal.entidades.Curso;
import com.proyectoFinal.enums.Idioma;
import com.proyectoFinal.enums.Nivel;
import com.proyectoFinal.servicios.CursoServicio;
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
    public String formulario() {
        return "form-curso";
    }

    @PostMapping("/form-curso")
    public String guardar(ModelMap model, @RequestParam String nombre, @RequestParam Nivel nivel, @RequestParam Idioma idioma, @RequestParam String idProfesor) {

        try {
            cursoServicio.crear(nombre, nivel, idioma, idProfesor);

            return "redirect:/curso/index";
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

    @GetMapping("/nivel-cursos/{nivel}")
    public String mostrarXnivel(ModelMap modelo, @PathVariable Nivel nivel) {
        try {
            List<Curso> listaCurso = cursoServicio.listarXnivel(nivel);
            modelo.put("cursoXnivel", listaCurso);

        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "index";
    }

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
