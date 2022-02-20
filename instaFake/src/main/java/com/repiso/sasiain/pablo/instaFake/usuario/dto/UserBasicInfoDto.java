package com.repiso.sasiain.pablo.instaFake.usuario.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class UserBasicInfoDto {

    private String nick;
    private String fotoPerfil;
    private UUID id;
}
