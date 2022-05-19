package com.proyectoFinal.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

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
    public String cursosXniveles() {
        return "nivel-cursos.html";
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
