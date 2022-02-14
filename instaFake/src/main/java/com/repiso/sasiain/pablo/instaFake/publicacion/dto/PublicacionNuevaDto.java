package com.repiso.sasiain.pablo.instaFake.publicacion.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@Builder
public class PublicacionNuevaDto {

    @NotEmpty
    private String titulo;
    @NotEmpty
    private String descripcion;
    @Builder.Default
    private boolean isPrivate=true;
    private String resource;
}
