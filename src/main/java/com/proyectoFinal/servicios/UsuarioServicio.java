package com.proyectoFinal.servicios;

import com.proyectoFinal.entidades.Foto;
import com.proyectoFinal.entidades.Usuario;
import com.proyectoFinal.enums.Pais;
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
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    FotoServicio fotoServicio;

    @Transactional(rollbackFor = {Exception.class})
    public Usuario registrar(MultipartFile archivo, String nombre, String apellido, Integer dni, String email, Integer telefono, String password, String region, Pais pais, Rol rol) throws Exception {

//        validar(nombre, apellido, dni, telefono, email, password);
        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDni(dni);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        String claveEncriptada = new BCryptPasswordEncoder().encode(password);
        usuario.setPassword(claveEncriptada);
        usuario.setRegion(region);
        usuario.setRol(rol);
        usuario.setPais(pais);
        usuario.setAlta(new Date());
        usuario.setBaja(null);

        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);

        return usuarioRepositorio.save(usuario);

    }

    @Transactional(rollbackFor = {Exception.class})
    public void modificar(MultipartFile archivo, String id, String nombre, String apellido, Integer dni, String email, Integer telefono, String region, Pais pais) throws Exception {

//        validar(nombre, apellido, dni, telefono, email, password);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setDni(dni);
            usuario.setEmail(email);
            usuario.setTelefono(telefono);
//            String claveEncriptada = new BCryptPasswordEncoder().encode(password);
//            usuario.setPassword(claveEncriptada);
            usuario.setRegion(region);
            usuario.setPais(pais);
            String idFoto = null;
            if (usuario.getFoto() != null) {

                idFoto = usuario.getFoto().getId();

            }
            Foto foto = fotoServicio.modificar(archivo, idFoto);
            usuario.setFoto(foto);

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
    public List<Usuario> listarProfesores() {
        return usuarioRepositorio.listarProfesor(Rol.PROFESOR);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarUsuariosActivos() {
        return usuarioRepositorio.buscarActivos();
    }

    @Transactional(readOnly = true)
    public Usuario BuscarId(String id) throws Exception {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new Exception("No ha encontrado el usuario");
        }

    }

    @Transactional(readOnly = true)
    public Usuario buscarProfesor(String id, Rol rol) {
        return usuarioRepositorio.buscarProfesor(id, rol);
    }

    @Transactional(readOnly = true)
    public Usuario listarProfesor(String id, Rol rol) {
        return usuarioRepositorio.buscarProfesor(id, rol);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarAlumnos() {
        return usuarioRepositorio.buscarAlumnos(Rol.ALUMNO);
    }

    @Transactional(rollbackFor = Exception.class)
    public Usuario deshabilitar(String id) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            throw new Exception("El ID no puede ser nulo");
        }

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());
            return usuarioRepositorio.save(usuario);
        } else {
            throw new Exception("No se pudo encontrar el usuario solicitado");
        }
    }

    public Usuario agregarNota(String idAlumno, String nota) throws Exception {
     
        Usuario usuario = BuscarId(idAlumno);
        usuario.getNotas().add(nota);
     
        return usuarioRepositorio.save(usuario);
    }

//    public List<String> traerNotas(String id, List<String> notas) {
//
//        return usuarioRepositorio.buscarNotas(id);
//    }
    @Transactional(rollbackFor = Exception.class)
    public Usuario habilitar(String id) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            throw new Exception("El ID no puede ser nulo");
        }

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setBaja(null);
            return usuarioRepositorio.save(usuario);
        } else {
            throw new Exception("No se pudo encontrar el usuario solicitado");
        }
    }

//        @Transactional(rollbackFor = Exception.class)
//    public void añadirNota(String id) throws Exception {
//
//        if (id != null) {
//         
//            Usuario usuario = usuarioRepositorio.getById(id);
//
//       List<String> notas = new ArrayList();
//      
//           usuario.getNotas().add(notas);
//
//        } else {
//            throw new Exception("No existe el alumno");
//        }
//
//    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario u = usuarioRepositorio.buscarPorEmail(email);

//        if (u == null) {
//            return null;
//        }
        if (u != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

//            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + u.getRol().toString());
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + u.getRol());
            permisos.add(p1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", u);

            return new User(u.getEmail(), u.getPassword(), permisos);
        } else {
            return null;
        }

    }

    private void validar(String nombre, String apellido, Integer dni, Integer telefono, String email, String password) throws Exception {
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
        if (telefono == null || telefono < 10) {
            throw new Exception("El numero de telefono ingresado no es correcto");
        }
        if (password == null || password.trim().isEmpty() || password.length() < 10) {
            throw new Exception("La contraseña debe tener 10 o más caracteres");
        }

    }
}
