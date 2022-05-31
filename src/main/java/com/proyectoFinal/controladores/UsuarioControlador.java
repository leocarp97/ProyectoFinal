package com.proyectoFinal.controladores;

import com.proyectoFinal.entidades.Usuario;
import com.proyectoFinal.enums.Pais;
import com.proyectoFinal.servicios.UsuarioServicio;
import java.util.List;
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

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/list-usuario")
    public String listarUsuarios(ModelMap model) throws Exception {

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();

        model.addAttribute("usuarios", usuarios);

        return "list-usuario";
    }
///terminar

//    @GetMapping("/añadir-nota/{id}")
//    public String añadirNota(ModelMap model, @PathVariable String id) throws Exception {
//
//        List<String> notas = new ArrayList();
//        notas.add("10");
//        notas.add("10");
//        notas.add("10");
//        notas.add("10");
//
//           model.addAttribute("notas", notas);
//        
//        usuarioServicio.añadirNota(id);
//    
//        return "añadir-nota";
//    }
//    @GetMapping("/list-alumnos")
//    public String listAlumnos(ModelMap model, @RequestParam String id) throws Exception {
//
//        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
//
//        
//        
////        List<Integer> nota = usuarioServicio.traerNotas(id);
////      
//
////        for (Usuario usuario : usuarios) {
////          usuario.getNotas();
////        }
////        usuarioServicio.añadirNota(id);
//
////        model.addAttribute("nota", nota);
//        model.addAttribute("usuarios", usuarios);
//
//        return "list-alumnos";
//    }

    @GetMapping("/list-usuario-activos")
    public String listarUsuariosActivos(ModelMap model) {

        List<Usuario> usuarios = usuarioServicio.buscarUsuariosActivos();

        model.addAttribute("usuarios", usuarios);

        return "list-usuario";
    }

    @GetMapping("/index")
    public String formulario() {
        return "index";
    }

    @PostMapping("/index")
    public String guardar(ModelMap model, @RequestParam(required = false) MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam Integer dni, @RequestParam String email, @RequestParam Integer telefono, @RequestParam String password, @RequestParam String region, @RequestParam Pais pais) {

        try {
            usuarioServicio.registrar(archivo, nombre, apellido, dni, email, telefono, password, region, pais);

            return "redirect:/usuario/list-usuario/";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/eliminar-usuario/{id}")
    public String eliminar(@PathVariable String id) throws Exception {

        usuarioServicio.deshabilitar(id);

        return "redirect:/usuario/list-usuario/";

    }

    @GetMapping("/habilitar-usuario/{id}")
    public String habilitar(@PathVariable String id) throws Exception {

        usuarioServicio.habilitar(id);

        return "redirect:/usuario/list-usuario/";

    }

    @PreAuthorize("hasAnyRole('ROLE_ALUMNO','ROLE_USUARIO')")
    @GetMapping("/editar-perfil/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) throws Exception {
        Usuario u = usuarioServicio.BuscarId(id);
        modelo.put("usuario", u);
        return "editar-perfil";
    }

    @PostMapping("/actualizar-usuario")
    public String editar(ModelMap modelo, MultipartFile archivo, @RequestParam String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam Integer dni, @RequestParam String email, @RequestParam Integer telefono, @RequestParam String password, @RequestParam String region, @RequestParam Pais pais) {
        try {
            usuarioServicio.modificar(archivo, id, nombre, apellido, dni, email, telefono, password, region, pais);
            modelo.put("exito", "se pudo actualizar");

            return "redirect:/usuario/editar-perfil/"+ id;

        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "index";
    }

    @PreAuthorize("hasAnyRole('ROLE_ALUMNO','ROLE_USUARIO')")
    @GetMapping("/añadir-nota/{id}")
    public String añadirNota(@PathVariable String id, ModelMap modelo) throws Exception {
        Usuario u = usuarioServicio.BuscarId(id);
        modelo.put("usuario", u);
        return "añadir-nota";
    }

    @PostMapping("/añadir-notas")
    public String añadirNota(ModelMap modelo, @RequestParam String id, @RequestParam String notas) {
        try {
            usuarioServicio.agregarNota(id, notas);
       
            modelo.put("exito", "se pudo actualizar");
            return "redirect:/curso/list-curso";

        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            e.printStackTrace();
        }
        return "index";
    }

}
