/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectoFinal.controladores;

import com.proyectoFinal.entidades.Usuario;
import com.proyectoFinal.enums.Rol;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Usuario
 */
@Controller
@RequestMapping("/resolver")
public class ResolverControlador {
    
    @GetMapping
    public String resolver(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        
        if (usuario.getRol().equals(Rol.ALUMNO)) return "redirect:/campus-alumno";
        if (usuario.getRol().equals(Rol.PROFESOR)) return "redirect:/campus-profesor";
        if (usuario.getRol().equals(Rol.ADMIN)) return "redirect:/campus-admin";
        
        return "redirect:/logout";
    }
    
}
