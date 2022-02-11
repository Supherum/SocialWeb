package com.repiso.sasiain.pablo.instaFake.usuario.dto.auth;

import com.repiso.sasiain.pablo.instaFake.usuario.model.Role;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;

public class UsuarioRegisterDtoConverter {

    public Usuario usuarioDtoToUsuario (UsuarioRegisterDto dto, Role role){
        return Usuario.builder().build();
    }
}
