package com.proyectoFinal.servicios;

import com.proyectoFinal.entidades.Usuario;
import com.proyectoFinal.enums.Rol;
import com.proyectoFinal.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
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
    public Usuario registrar(String nombre, String apellido, Integer dni, String email, Integer telefono, String password) throws Exception {

        validar(nombre, apellido, dni, email, telefono, password);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDni(dni);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        String claveEncriptada = new BCryptPasswordEncoder().encode(password);
        usuario.setPassword(claveEncriptada);
        usuario.setRol(Rol.ALUMNO);
        usuario.setAlta(new Date());
        usuario.setBaja(null);

        return usuarioRepositorio.save(usuario);

    }

    @Transactional(rollbackFor = {Exception.class})
    public void modificar(String id, String nombre, String apellido, Integer dni, String email, Integer telefono, String password) throws Exception {

        validar(nombre, apellido, dni, email, telefono, password);
        
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
    public Usuario BuscarId(String id) throws Exception {
        Optional<Usuario> respuesta =  usuarioRepositorio.findById(id);
        if(respuesta.isPresent())    {
            return respuesta.get();
        }else{  
            throw new Exception("No ha encontrado el usuario");
        }
        
    }
    
    @Transactional(readOnly = true)
    public Usuario buscarProfesor(String id, Rol rol) {
        return usuarioRepositorio.buscarProfesor(id, rol);
    }
    
    @Transactional(readOnly = true)
    public List<Usuario> buscarAlumnos(Rol rol) {
        return usuarioRepositorio.buscarAlumnos(rol);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Usuario deshabilitar(String id) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            throw new Exception("El ID no puede ser nulo");
        }
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());
            return usuarioRepositorio.save(usuario);
        } else {
            throw new Exception("No se pudo encontrar el libro solicitado");
        }
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Usuario habilitar(String id) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            throw new Exception("El ID no puede ser nulo");
        }
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setBaja(null);
            return usuarioRepositorio.save(usuario);
        } else {
            throw new Exception("No se pudo encontrar el libro solicitado");
        }
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

    private void validar(String nombre, String apellido, Integer dni, String email, Integer telefono, String password) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("Debe ingresar su nombre");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new Exception("Debe ingresar su apellido");
        }
        if (dni == null || dni < 8) {
            throw new Exception("El dni no puede ser nulo y/o menor a 8 caracteres");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new Exception("Debe ingresar su correo electrónico");
        }
        if (telefono == null || dni < 10) {
            throw new Exception("El numero de telefono ingresado no es correcto");
        }
        if (password == null || password.trim().isEmpty() || password.length() < 10) {
            throw new Exception("La contraseña debe tener 10 o más caracteres");
        }
    }
}
