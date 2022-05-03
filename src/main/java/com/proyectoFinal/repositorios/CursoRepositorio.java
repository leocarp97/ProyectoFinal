package com.proyectoFinal.repositorios;

import com.proyectoFinal.entidades.Curso;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepositorio extends JpaRepository<Curso, String> {
    
   @Query("SELECT c FROM Cursos c WHERE c.baja IS NULL") 
    public List<Curso> buscarActivos();
}
