package com.repiso.sasiain.pablo.instaFake.usuario.controller;

import com.repiso.sasiain.pablo.instaFake.usuario.dto.auth.UsuarioRegisterDto;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.auth.UsuarioRegisterDtoConverter;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Role;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import com.repiso.sasiain.pablo.instaFake.usuario.service.UsuarioAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class UsuarioRegisterController {

    private final UsuarioAuthService usuarioAuthService;
    private final UsuarioRegisterDtoConverter usuarioRegisterDtoConverter;


    @PostMapping("/usuario")
    public Usuario registerUsuario (@Valid @RequestBody UsuarioRegisterDto dto){
        return usuarioAuthService.saveUsuario(dto,Role.USER);
    }
}
