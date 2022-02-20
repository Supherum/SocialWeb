package com.repiso.sasiain.pablo.instaFake.publicacion.dto;

import com.repiso.sasiain.pablo.instaFake.publicacion.model.Publicacion;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.UserBasicInfoDto;
import org.springframework.stereotype.Component;

@Component
public class PublicacionResponseDtoConverter {

    public PublicacionResponseDto publicacionToPublicacionResponseDto (Publicacion publicacion, UserBasicInfoDto dto){
        return PublicacionResponseDto.builder()
                .titulo(publicacion.getTitulo())
                .descipcion(publicacion.getDescripcion())
                .id(publicacion.getId())
                .listaRecursos(publicacion.getListrecurso())
                .isPrivate(publicacion.isPrivate())
                .nick(dto.getNick())
                .fotoPerfil(dto.getFotoPerfil())
                .idUsuario(dto.getId())
                .build();
    }
}
