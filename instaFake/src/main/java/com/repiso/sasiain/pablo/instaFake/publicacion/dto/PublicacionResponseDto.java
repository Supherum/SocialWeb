package com.repiso.sasiain.pablo.instaFake.publicacion.dto;

import lombok.*;

import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PublicacionResponseDto {

    private UUID id;
    private UUID idUsuario;
    private String nick;
    private String fotoPerfil;
    private String titulo;
    @Lob
    private String descipcion;
    private boolean isPrivate;
    @Builder.Default
    private List<String> listaRecursos=new ArrayList<>();
}
