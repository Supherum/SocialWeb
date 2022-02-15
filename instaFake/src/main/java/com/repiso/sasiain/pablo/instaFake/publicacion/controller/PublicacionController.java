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

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        List<String> nombreArchivos=fileService.saveFileWithCopy(file,1024);
        String uri1 =fileService.getUri(nombreArchivos.get(0));
        String uri2 =fileService.getUri(nombreArchivos.get(1));
        dto.setResource(new ArrayList<>());
        dto.getResource().add(uri1);
        dto.getResource().add(uri2);

    return ResponseEntity.status(HttpStatus.CREATED).body(publicacionServicio.guardarNuevaPublicacion(dto,usuario));
    }


    @PutMapping("/{id}")
    public PublicacionResponseDto editarPublicacion (@Valid @RequestPart ("publicacion") PublicacionNuevaDto dto,
                                                     @RequestPart ("file") MultipartFile file,
                                                     @AuthenticationPrincipal Usuario usuario){


        return null;
    }
}
