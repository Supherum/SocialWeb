package com.repiso.sasiain.pablo.instaFake.usuario.controller;

import com.repiso.sasiain.pablo.instaFake.shared.file.service.FileService;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.SolicitudesDtoResponse;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.UsuarioEditDto;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.UsuarioPerfilResponse;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.auth.UsuarioLoginDtoResponse;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.auth.UsuarioLoginDtoResponseConverter;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.auth.UsuarioRegisterDto;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import com.repiso.sasiain.pablo.instaFake.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioLoginDtoResponseConverter usuarioLoginDtoResponseConverter;
    private final FileService fileService;

    @GetMapping("/{id}")
    public UsuarioPerfilResponse verPerfil (@AuthenticationPrincipal Usuario usuario, @PathVariable ("id") UUID id){
        return usuarioService.perfilDeUsuario(usuario,id);
    }

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

    @GetMapping("/seguidores")
    public List<SolicitudesDtoResponse> listaDePeticionesDeUsuariosPendientes(){
        return usuarioService.listaDePeticionesPendientes();
    }

    @PutMapping("/me")
    public UsuarioLoginDtoResponse editarMiPerfil (@AuthenticationPrincipal Usuario usuario,
                                                   @Valid @RequestPart ("usuario") UsuarioEditDto dto,
                                                   @RequestPart ("file")MultipartFile file) throws IOException {


        return usuarioLoginDtoResponseConverter.UserAndTokenToUsuarioDtoResponse(usuarioService.save(dto,file,usuario),null);
    }

    @PostMapping ("/like/{id}")
    public ResponseEntity<?> addLikeToPublicacion (@AuthenticationPrincipal Usuario usuario,@PathVariable ("id") UUID id){

        return ResponseEntity.ok(usuarioService.likePublicacion(usuario,id));
    }


}
