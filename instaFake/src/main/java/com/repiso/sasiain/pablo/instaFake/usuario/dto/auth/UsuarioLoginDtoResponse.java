package com.repiso.sasiain.pablo.instaFake.usuario.dto.auth;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class UsuarioLoginDtoResponse {

    private UUID id;
    private String nick;
    private String nombre;
    private String apellidos;
    private String rol;
    private String fotoPerfil;
    private String tokenJwt;
    private boolean isPrivate;
    private String direccion,telefono,ciudad;
    private LocalDate fechaDeNacimiento;

}
