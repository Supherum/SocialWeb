package com.repiso.sasiain.pablo.instaFake.publicacion.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PublicacionResponseDto {

    private UUID id;
    private String titulo;
    private String descipcion;
    private boolean isPrivate;
    private String recurso;
}
