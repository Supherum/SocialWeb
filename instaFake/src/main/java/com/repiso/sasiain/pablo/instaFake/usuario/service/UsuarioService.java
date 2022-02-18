package com.repiso.sasiain.pablo.instaFake.usuario.service;

import com.repiso.sasiain.pablo.instaFake.usuario.dto.SolicitudesDtoResponse;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.UsuarioEditDto;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.UsuarioPerfilResponse;
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

    String solicitarSeguirUsuario (String nick,Usuario usuario);

    String aceptarSolicitudUsuario(Usuario u, UUID id);

    String declinarSolicitudUsuario(Usuario usuario,UUID id);

    List<SolicitudesDtoResponse> listaDePeticionesPendientes();

    UsuarioPerfilResponse perfilDeUsuario(Usuario usuario,UUID id);

    Usuario save(UsuarioRegisterDto usuario, MultipartFile file) throws IOException;

    Usuario save(UsuarioEditDto dto, MultipartFile file, Usuario usuario) throws IOException;

    String likePublicacion (Usuario usuario,UUID id);

}
