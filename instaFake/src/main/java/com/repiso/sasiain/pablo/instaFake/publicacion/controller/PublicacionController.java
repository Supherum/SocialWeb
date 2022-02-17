package com.repiso.sasiain.pablo.instaFake.publicacion.controller;

import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionNuevaDto;
import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionResponseDto;
import com.repiso.sasiain.pablo.instaFake.publicacion.model.Publicacion;
import com.repiso.sasiain.pablo.instaFake.publicacion.servicio.PublicacionServicio;
import com.repiso.sasiain.pablo.instaFake.shared.file.service.FileService;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import io.github.techgnious.exception.VideoException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/publicacion")
public class PublicacionController {

    private final PublicacionServicio publicacionServicio;
    private final FileService fileService;

    @PostMapping("/")
    public ResponseEntity<PublicacionResponseDto> nuevaPublicacion (@Valid @RequestPart ("publicacion") PublicacionNuevaDto dto,
                                                                    @RequestPart ("file") MultipartFile file,
                                                                    @AuthenticationPrincipal Usuario usuario) throws IOException, VideoException {

        dto=publicacionServicio.addResourceToDto(file,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(publicacionServicio.guardarNuevaPublicacion(dto,usuario));
    }


    @PutMapping("/{id}")
    public PublicacionResponseDto editarPublicacion (@PathVariable("id") UUID id,
                                                     @Valid @RequestPart ("publicacion") PublicacionNuevaDto dto,
                                                     @RequestPart ("file") MultipartFile file,
                                                     @AuthenticationPrincipal Usuario usuario) throws IOException, VideoException {

        dto=publicacionServicio.addResourceToDto(file,dto);
        return publicacionServicio.editarPublicacion(id,dto,usuario);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> borrarPublicacion (@PathVariable("id") UUID id,
                                                @AuthenticationPrincipal Usuario usuario) throws IOException, VideoException {

        publicacionServicio.deletePublicacion(id,usuario);
        return  ResponseEntity.ok().build();
    }

    @GetMapping("/public")
    public List<PublicacionResponseDto> todasLasPublicacionesPublicas (@AuthenticationPrincipal Usuario usuario){

        return publicacionServicio.getAllPublicaciones();
    }

    @GetMapping ("/{id}")
    public PublicacionResponseDto getPublicacion (@PathVariable("id") UUID id,@AuthenticationPrincipal Usuario usuario){
        return publicacionServicio.getPublicacion(id,usuario);
    }


    @GetMapping("/usuario/{nick}")
    public List<PublicacionResponseDto> getPublicacionesByNick (@PathVariable ("nick") String nick,
                                                                @AuthenticationPrincipal Usuario usuario){
        return publicacionServicio.getPublicacionesUsuarioPorNick(nick,usuario);
    }

    @GetMapping("/mia")
    public List<PublicacionResponseDto> getMisPublicaciones (@AuthenticationPrincipal Usuario usuario){
        return  publicacionServicio.getPublicacionesUsuarioPorNick(usuario.getNick(),usuario);
    }

}
