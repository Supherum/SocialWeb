package com.repiso.sasiain.pablo.instaFake.publicacion.dto;

import com.repiso.sasiain.pablo.instaFake.publicacion.model.Publicacion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublicacionNuevaDtoConverter {

    public Publicacion PublicacionNuevaDtoToPublicacion (PublicacionNuevaDto dto){
        return Publicacion.builder()
                .descripcion(dto.getDescripcion())
                .titulo(dto.getTitulo())
                .listrecurso(List.of(dto.getResource().get(0),dto.getResource().get(1)))
                .isPrivate(dto.isPrivate())
                .build();
    }

    public Publicacion publicacionNuevaDtoEditPublicacion (PublicacionNuevaDto dto,Publicacion publicacion){
        publicacion.setDescripcion(dto.getDescripcion());
        publicacion.setListrecurso(dto.getResource());
        publicacion.setPrivate(dto.isPrivate());
        publicacion.setTitulo(dto.getTitulo());
       return publicacion;
    }
}
