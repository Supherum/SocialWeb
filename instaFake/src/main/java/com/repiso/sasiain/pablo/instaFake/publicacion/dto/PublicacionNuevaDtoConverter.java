package com.repiso.sasiain.pablo.instaFake.publicacion.dto;

import com.repiso.sasiain.pablo.instaFake.publicacion.model.Publicacion;
import org.springframework.stereotype.Component;

@Component
public class PublicacionNuevaDtoConverter {

    public Publicacion PublicacionNuevaDtoToPublicacion (PublicacionNuevaDto dto){
        return Publicacion.builder()
                .descripcion(dto.getDescripcion())
                .titulo(dto.getTitulo())
                .build();
    }
}
