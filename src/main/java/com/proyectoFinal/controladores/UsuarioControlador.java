package com.proyectoFinal.controladores;

import com.proyectoFinal.entidades.Usuario;
import com.proyectoFinal.enums.Pais;
import com.proyectoFinal.enums.Rol;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
<<<<<<< HEAD
///terminar
    @GetMapping("/list-alumnos")
    public String listAlumnos(ModelMap model) {
        List<Usuarios> usuarios = 
        List<String> hola = new ArrayList();
        List<Double> notas = usuarioServicio.BuscarId(id).getNotas();
        model.addAttribute("notas", notas);
         model.addAttribute("usuarios", usuarios);

=======


    
    @GetMapping("/list-alumnos")
    public String listarAlumnos(ModelMap model) throws Exception {



        List<Usuario> usuarios = usuarioServicio.buscarAlumnos();

        model.addAttribute("usuarios", usuarios);

        return "list-usuario";
    }

    @GetMapping("/list-profesores")
    public String listarProfesores(ModelMap model) throws Exception {

        List<Usuario> usuarios = usuarioServicio.listarProfesores();

        model.addAttribute("usuarios", usuarios);

        return "list-usuario";
>>>>>>> 15334ef8f8c45c7b5d2fb44d01cc8adfb9aad7a6
    }

    @GetMapping("/list-usuario-activos")
    public String listarUsuariosActivos(ModelMap model) {

        List<Usuario> usuarios = usuarioServicio.buscarUsuariosActivos();

        model.addAttribute("usuarios", usuarios);

        return "list-usuario";
    }


//    @GetMapping("/index")
//    public String formulario() {
//        return "index";
//    }

//    @PostMapping("/index")
//    public String guardar(ModelMap model, @RequestParam(required = false) MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam(required = false) Integer dni, @RequestParam String email, @RequestParam Integer telefono, @RequestParam String password, @RequestParam String region, @RequestParam Pais pais, @RequestParam (required = false) Rol rol) {
//
//        try {
//            usuarioServicio.registrar(archivo, nombre, apellido, dni, email, telefono, password, region, pais, Rol.ALUMNO);
//
//            return "redirect:/login";
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            model.put("error", e.getMessage());
//            return "redirect:/";
//        }
//    }

    
    @PostMapping("/campus-admin")
    public String guardarProfesor(ModelMap model, @RequestParam(required = false) MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam Integer dni, @RequestParam String email, @RequestParam Integer telefono, @RequestParam String password, @RequestParam String region, @RequestParam Pais pais, @RequestParam (required = false) Rol rol) {

        try {
            usuarioServicio.registrar(archivo, nombre, apellido, dni, email, telefono, password, region, pais, Rol.PROFESOR);

            return "redirect:/";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/eliminar-usuario/{id}")
    public String eliminar(@PathVariable String id) throws Exception {

        usuarioServicio.deshabilitar(id);


        if(usuarioServicio.BuscarId(id).getRol().equals(Rol.ALUMNO)){
            return "redirect:/usuario/list-alumnos/";
        } else {
            return "redirect:/usuario/list-profesores/";
        }

    }

    @GetMapping("/habilitar-usuario/{id}")
    public String habilitar(@PathVariable String id) throws Exception {

        usuarioServicio.habilitar(id);

        
        if(usuarioServicio.BuscarId(id).getRol().equals(Rol.ALUMNO)){
            return "redirect:/usuario/list-alumnos/";
        } else {
            return "redirect:/usuario/list-profesores/";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_ALUMNO','ROLE_PROFESOR','ROLE_ADMIN')")
    @GetMapping("/editar-perfil/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) throws Exception {
        Usuario u = usuarioServicio.BuscarId(id);
        modelo.put("usuario", u);
        return "editar-perfil";
    }

    @PostMapping("/actualizar-usuario")
    public String editar(RedirectAttributes attr,ModelMap modelo, MultipartFile archivo, @RequestParam String id, @RequestParam(required = false) String nombre, @RequestParam(required = false) String apellido, @RequestParam(required = false) Integer dni, @RequestParam(required = false) String email, @RequestParam(required = false) Integer telefono, @RequestParam(required = false) String region, @RequestParam(required = false) Pais pais) {
        try {
            usuarioServicio.modificar(archivo, id, nombre, apellido, dni, email, telefono, region, pais);
            attr.addFlashAttribute("exito", "se pudo actualizar con exito");
            
            return "redirect:/usuario/editar-perfil/" + id;

        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/usuario/editar-perfil/" + id;
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESOR')")
    @GetMapping("/a??adir-nota/{id}")
    public String a??adirNota(@PathVariable String id, ModelMap modelo) throws Exception {
        Usuario u = usuarioServicio.BuscarId(id);
        modelo.put("usuario", u);
        return "a??adir-nota";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESOR')")
    @PostMapping("/a??adir-notas")
    public String a??adirNota(ModelMap modelo, @RequestParam String id, @RequestParam String notas) {
        try {
            usuarioServicio.agregarNota(id, notas);

            modelo.put("exito", "se pudo actualizar");
            return "redirect:/curso/list-alumnos";

        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "index";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ALUMNO','ROLE_PROFESOR','ROLE_ADMIN')")
    @GetMapping("/perfil/{id}")
    public String verInfo(@PathVariable String id, ModelMap modelo) throws Exception {
        Usuario u = usuarioServicio.BuscarId(id);
        modelo.put("usuario", u);
        return "perfil";
    }

}
