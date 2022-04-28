package com.proyectoFinal.servicios;

import com.proyectoFinal.entidades.Usuario;
import com.proyectoFinal.enums.Rol;
import com.proyectoFinal.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Transactional(rollbackFor = {Exception.class})
    public Usuario crear(String nombre, String apellido, Integer dni, String email, Integer telefono, String password) throws Exception {

        validarUsuario(nombre, apellido, dni);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDni(dni);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        String claveEncriptada = new BCryptPasswordEncoder().encode(password);
        usuario.setPassword(claveEncriptada);
        usuario.setRol(Rol.ALUMNO);

        return usuarioRepositorio.save(usuario);

    }

    @Transactional(rollbackFor = {Exception.class})
    public void modificar(String id, String nombre, String apellido, Integer dni, String email, Integer telefono, String password) throws Exception {

        validarUsuario(nombre, apellido, dni);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setDni(dni);
            usuario.setEmail(email);
            usuario.setTelefono(telefono);
            String claveEncriptada = new BCryptPasswordEncoder().encode(password);
            usuario.setPassword(claveEncriptada);
            usuario.setRol(Rol.ALUMNO);
            usuarioRepositorio.save(usuario);

        } else {
            throw new Exception("No se encontro el usuario buscado");
        }
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {

        return usuarioRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario BuscarId(String id) {
        Optional<Usuario> respuesta =  usuarioRepositorio.findById(id);
        if(respuesta.isPresent())    {
            return respuesta.get();
        }else{  
            throw new Exception("No ha encontrado el usuario");
        }
        
    }

    public void eliminarUsuario(String id) {

        usuarioRepositorio.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario u = usuarioRepositorio.buscarPorEmail(email);

        if (u == null) {
            return null;
        }

        List<GrantedAuthority> permisos = new ArrayList<>();

        GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + u.getRol().toString());
        permisos.add(p1);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("usuariosession", u);

        return new User(u.getEmail(), u.getPassword(), permisos);
    }

    private void validarUsuario(String nombre, String apellido, Integer dni) throws Exception {
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre no puede ser nulo");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new Exception("El apellido no puede ser nulo");
        }
        if (dni == null) {
            throw new Exception("El dni no puede ser nulo");
        }

    }
}
