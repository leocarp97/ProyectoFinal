package com.proyectoFinal.repositorios;

import com.proyectoFinal.entidades.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepositorio extends JpaRepository<Curso, String> {
    
}
