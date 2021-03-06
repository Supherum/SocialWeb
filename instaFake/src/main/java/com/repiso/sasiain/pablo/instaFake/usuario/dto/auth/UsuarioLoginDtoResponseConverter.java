package com.repiso.sasiain.pablo.instaFake.usuario.dto.auth;

import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UsuarioLoginDtoResponseConverter {

    public UsuarioLoginDtoResponse UserAndTokenToUsuarioDtoResponse (Usuario usuario,String token){
        return UsuarioLoginDtoResponse.builder()
                .id(usuario.getId())
                .nick(usuario.getNick())
                .nombre(usuario.getNombre())
                .apellidos(usuario.getApellidos())
                .rol(usuario.getRole().name())
                .tokenJwt(token)
                .fotoPerfil(usuario.getFotoPerfil())
                .isPrivate(usuario.isPrivado())
                .direccion(usuario.getDireccion())
                .telefono(usuario.getTelefono())
                .ciudad(usuario.getCiudad())
                .fechaDeNacimiento(usuario.getFechaNaciemiento())
                .build();
    }
}
