package com.proyectoFinal.repositorios;

import com.proyectoFinal.entidades.Usuario;
import com.proyectoFinal.enums.Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuarios u WHERE u.email LIKE :email")
    public Usuario buscarPorEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuarios u WHERE u.rol LIKE :alumno")
    public List<Usuario> buscarAlumnos(@Param("alumno") Rol rol);

    @Query("SELECT u FROM Usuarios u WHERE u.id LIKE :id AND u.rol LIKE :profesor")
    public Usuario buscarProfesor(@Param("id") String id, @Param("profesor") Rol rol);

    @Query("SELECT u FROM Usuarios u WHERE u.id LIKE :id AND u.rol LIKE :alumno")
    public Usuario buscarAlumno(@Param("id") String id, @Param("alumno") Rol rol);

    @Query("SELECT u FROM Usuarios u WHERE u.baja IS NULL")
    public List<Usuario> buscarActivos();

}
