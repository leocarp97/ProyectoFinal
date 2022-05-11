package com.proyectoFinal.repositorios;

import com.proyectoFinal.entidades.Curso;
import com.proyectoFinal.enums.Nivel;
import com.proyectoFinal.enums.Turno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepositorio extends JpaRepository<Curso, String> {
    
   @Query("SELECT c FROM Curso c WHERE c.baja IS NULL") 
    public List<Curso> buscarActivos();
    
    @Query("SELECT c FROM Curso c WHERE c.nivel LIKE : nivel")
    public List<Curso> buscarPorNivel(@Param("nivel") Nivel nivel);
    
    
    @Query("SELECT c FROM Curso c Where c.nivel LIKE : turno ")
    public List<Curso> buscarPorTurno(@Param("turno") Turno turno );
}
