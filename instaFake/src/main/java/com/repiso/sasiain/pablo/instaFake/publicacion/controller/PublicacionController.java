package com.repiso.sasiain.pablo.instaFake.publicacion.controller;

import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionNuevaDto;
import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionResponseDto;
import com.repiso.sasiain.pablo.instaFake.publicacion.servicio.PublicacionServicio;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publicacion")
public class PublicacionController {

    private final PublicacionServicio publicacionServicio;

    @PostMapping("/")
    public ResponseEntity<PublicacionResponseDto> nuevaPublicacion (@Valid @RequestBody PublicacionNuevaDto dto, @AuthenticationPrincipal Usuario usuario){

    return ResponseEntity.status(HttpStatus.CREATED).body(publicacionServicio.guardarPublicacion(dto,usuario));
    }
}
