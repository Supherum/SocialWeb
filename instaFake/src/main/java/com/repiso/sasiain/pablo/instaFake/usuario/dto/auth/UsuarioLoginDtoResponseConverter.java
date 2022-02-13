package com.repiso.sasiain.pablo.instaFake.usuario.dto.auth;

import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioLoginDtoResponseConverter {

    public UsuarioLoginDtoResponse UserAndTokenToUsuarioDtoResponse (Usuario usuario,String token){
        return UsuarioLoginDtoResponse.builder()
                .nick(usuario.getNick())
                .nombre(usuario.getNombre())
                .apellidos(usuario.getApellidos())
                .rol(usuario.getRole().name())
                .tokenJwt(token)
                .build();
    }
}
