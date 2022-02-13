package com.repiso.sasiain.pablo.instaFake.publicacion.dto;

import com.repiso.sasiain.pablo.instaFake.publicacion.model.Publicacion;
import org.springframework.stereotype.Component;

@Component
public class PublicacionResponseDtoConverter {

    public PublicacionResponseDto publicacionToPublicacionResponseDto (Publicacion publicacion){
        return PublicacionResponseDto.builder()
                .titulo(publicacion.getTitulo())
                .descipcion(publicacion.getDescripcion())
                .id(publicacion.getId())
                .build();
    }
}
