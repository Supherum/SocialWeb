package com.repiso.sasiain.pablo.instaFake.usuario.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class UsuarioRegisterDto {

    private String nombre;
    private String email;
    private String apellidos;
    private String nick;
    private String password;
    private String password2;
    private String image;
}
