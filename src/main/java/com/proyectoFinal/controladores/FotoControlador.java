package com.proyectoFinal.controladores;

import com.proyectoFinal.entidades.Curso;
import com.proyectoFinal.entidades.Foto;
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
    
    @Autowired
    private FotoServicio fotoServicio;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> foto(@PathVariable String id) {

        try {
            
            Foto foto = fotoServicio.buscarPorId(id);
            
            byte[] contenido = foto.getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(contenido, headers, HttpStatus.OK);

        } catch (Exception ex) {
            Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE, null, ex);

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    

    
}
