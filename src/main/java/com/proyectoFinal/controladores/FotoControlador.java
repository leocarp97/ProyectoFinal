package com.proyectoFinal.controladores;

import com.proyectoFinal.entidades.Curso;
import com.proyectoFinal.entidades.Usuario;
import com.proyectoFinal.servicios.CursoServicio;
import com.proyectoFinal.servicios.FotoServicio;
import com.proyectoFinal.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foto")
public class FotoControlador {

    @Autowired
    CursoServicio cursoServicio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/usuario{id}")
    public ResponseEntity<byte[]> fotoUsuario(@PathVariable String id) {

        try {
            Usuario usuario = usuarioServicio.BuscarId(id);
            
            if (usuario.getFoto()==null) {
                throw new Exception("El usuario no tiene una foto asignada");
            }
            
            byte[] foto = usuario.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);

        } catch (Exception ex) {
            Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE, null, ex);

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    
    @GetMapping("/curso{id}")
    public ResponseEntity<byte[]> fotoCurso(@PathVariable String id) {

        try {
            Curso curso = cursoServicio.BuscarId(id);
            
            if (curso.getFoto()==null) {
                throw new Exception("El curso no tiene una foto asignada");
            }
            
            byte[] foto = curso.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);

        } catch (Exception ex) {
            Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE, null, ex);

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    
}
