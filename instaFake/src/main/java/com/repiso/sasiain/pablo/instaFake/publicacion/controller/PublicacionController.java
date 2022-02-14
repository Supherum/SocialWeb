package com.repiso.sasiain.pablo.instaFake.publicacion.controller;

import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionNuevaDto;
import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionResponseDto;
import com.repiso.sasiain.pablo.instaFake.publicacion.servicio.PublicacionServicio;
import com.repiso.sasiain.pablo.instaFake.shared.file.service.FileService;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publicacion")
public class PublicacionController {

    private final PublicacionServicio publicacionServicio;
    private final FileService fileService;

    @PostMapping("/")
    public ResponseEntity<PublicacionResponseDto> nuevaPublicacion (@Valid @RequestPart ("publicacion") PublicacionNuevaDto dto,
                                                                    @RequestPart ("file") MultipartFile file,
                                                                    @AuthenticationPrincipal Usuario usuario) throws IOException {

        String nombreArchivo=fileService.saveFileWithCopy(file,1024);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/resource/")
                .path(nombreArchivo)
                .toUriString();
        dto.setResource(uri);
    return ResponseEntity.status(HttpStatus.CREATED).body(publicacionServicio.guardarPublicacion(dto,usuario));
    }
}
