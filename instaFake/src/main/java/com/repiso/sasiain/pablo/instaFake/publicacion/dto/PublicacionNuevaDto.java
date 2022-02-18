package com.repiso.sasiain.pablo.instaFake.publicacion.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Builder
public class PublicacionNuevaDto {

    @NotEmpty(message = "{field.can.not.be.null}")
    private String titulo;
    @NotEmpty(message = "{field.can.not.be.null}")
    private String descripcion;
    @Builder.Default
    private boolean isPrivate=true;
    @Builder.Default
    private List<String> resource=new ArrayList<>();
}
