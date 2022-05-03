package com.proyectoFinal.servicios;

import com.proyectoFinal.entidades.Foto;
import com.proyectoFinal.repositorios.FotoRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {

    @Autowired
    FotoRepositorio fotoRepositorio;

    public Foto guardar(MultipartFile archivo) {

        if (archivo != null) {
            try {
                Foto foto = new Foto();
                foto.setNombre(archivo.getName());
                foto.setMime(archivo.getContentType());
                foto.setContenido(archivo.getBytes());
               
                fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        return null;
    }

    public Foto modificar(MultipartFile archivo, String id) {

        if (archivo != null) {
            try {

                Foto foto = new Foto();
                if (id != null) {
                    Optional<Foto> respuesta = fotoRepositorio.findById(id);

                if (respuesta.isPresent()) {
                    foto = respuesta.get();

                } 
                }
              

                foto.setNombre(archivo.getName());
                foto.setMime(archivo.getContentType());
                foto.setContenido(archivo.getBytes());

                fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        return null;

    }

}
