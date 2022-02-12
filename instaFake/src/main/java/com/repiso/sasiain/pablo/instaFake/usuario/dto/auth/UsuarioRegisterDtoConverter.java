package com.repiso.sasiain.pablo.instaFake.usuario.dto.auth;

import com.repiso.sasiain.pablo.instaFake.usuario.model.Role;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRegisterDtoConverter {

    public Usuario usuarioDtoToUsuario (UsuarioRegisterDto dto, Role role){

        return Usuario.builder()
                .nombre(dto.getNombre())
                .apellidos(dto.getApellidos())
                .email(dto.getEmail())
                .fechaNaciemiento(dto.getFechaNacimiento())
                .nick(dto.getNick())
                .password(dto.getPassword())
                .role(role)
                .build();
    }
}
