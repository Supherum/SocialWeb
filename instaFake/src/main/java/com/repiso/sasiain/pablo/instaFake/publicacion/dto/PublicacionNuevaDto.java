package com.repiso.sasiain.pablo.instaFake.publicacion.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class PublicacionNuevaDto {

    @NotEmpty
    private String titulo;
    @NotEmpty
    private String descripcion;
}
