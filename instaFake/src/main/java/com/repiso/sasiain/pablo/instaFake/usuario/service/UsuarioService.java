package com.repiso.sasiain.pablo.instaFake.usuario.service;

import com.repiso.sasiain.pablo.instaFake.usuario.dto.auth.UsuarioRegisterDto;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {

    public String solicitarSeguirUsuario (String nick,Usuario usuario);

    public String aceptarSolicitudUsuario(Usuario u, UUID id);

    public List<Usuario> findAll();

    public Page<Usuario> findAll(Pageable pageable);

    public Optional<Usuario> findById(UUID id);

    public Usuario save(UsuarioRegisterDto usuario, MultipartFile file) throws IOException;

    public Usuario edit (UsuarioRegisterDto usuario,MultipartFile file);

    public void delete(Usuario usuario);

    public void deleteById(UUID id);
}
