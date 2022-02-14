package com.repiso.sasiain.pablo.instaFake.usuario.dto.auth;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class UsuarioLoginDtoResponse {

    private String nick;
    private String nombre;
    private String apellidos;
    private String rol;
    private String fotoPerfil;
    private String tokenJwt;
}
