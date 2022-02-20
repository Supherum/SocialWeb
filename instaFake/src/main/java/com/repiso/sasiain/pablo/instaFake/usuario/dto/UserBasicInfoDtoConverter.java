package com.repiso.sasiain.pablo.instaFake.usuario.dto;

import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UserBasicInfoDtoConverter {

    public UserBasicInfoDto userToUserBasicInfoDto (Usuario usuario){
        return UserBasicInfoDto.builder()
                .id(usuario.getId())
                .nick(usuario.getNick())
                .fotoPerfil(usuario.getFotoPerfil())
                .build();
    }
}
