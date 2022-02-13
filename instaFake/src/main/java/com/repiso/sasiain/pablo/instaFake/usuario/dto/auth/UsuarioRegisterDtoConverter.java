package com.repiso.sasiain.pablo.instaFake.usuario.dto.auth;

import com.repiso.sasiain.pablo.instaFake.usuario.model.Role;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRegisterDtoConverter {

    @Autowired
    private  PasswordEncoder codificador;

    public Usuario usuarioDtoToUsuario (UsuarioRegisterDto dto, Role role){

        return Usuario.builder()
                .nombre(dto.getNombre())
                .apellidos(dto.getApellidos())
                .email(dto.getEmail())
                .fechaNaciemiento(dto.getFechaNacimiento())
                .nick(dto.getNick())
                .password(codificador.encode(dto.getPassword()))
                .role(role)
                .build();
    }
}
