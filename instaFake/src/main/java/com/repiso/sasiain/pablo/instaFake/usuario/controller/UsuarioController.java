package com.repiso.sasiain.pablo.instaFake.usuario.controller;

import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import com.repiso.sasiain.pablo.instaFake.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/seguir/{nick}")
    public ResponseEntity<?> seguirUsuario (@AuthenticationPrincipal Usuario usuario, @PathVariable ("nick") String nick){

        return ResponseEntity.ok(usuarioService.solicitarSeguirUsuario(nick,usuario));
    }

    @PostMapping("/seguir/aceptar/{id}")
    public ResponseEntity<?> acceptarUsuario (@AuthenticationPrincipal Usuario usuario, @PathVariable ("id") UUID id){

        return ResponseEntity.ok(usuarioService.aceptarSolicitudUsuario(usuario,id));
    }

    @DeleteMapping("/rechazar/{id}")
    public ResponseEntity<?> rechazarUsuario(@AuthenticationPrincipal Usuario usuario, @PathVariable ("id") UUID id){
        return ResponseEntity.ok(usuarioService.declinarSolicitudUsuario(usuario,id));
    }

}
