package com.repiso.sasiain.pablo.instaFake.usuario.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class SolicitudesDtoResponse {

    private String nick;
    private int numeroPeticionesPendientes;
}
