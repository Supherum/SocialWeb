package com.repiso.sasiain.pablo.instaFake.publicacion.servicio;

import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionNuevaDto;
import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionNuevaDtoConverter;
import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionResponseDto;
import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionResponseDtoConverter;
import com.repiso.sasiain.pablo.instaFake.publicacion.model.Publicacion;
import com.repiso.sasiain.pablo.instaFake.publicacion.repository.PublicacionRepository;
import com.repiso.sasiain.pablo.instaFake.shared.service.BaseService;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import com.repiso.sasiain.pablo.instaFake.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicacionServicio extends BaseService<Publicacion, UUID, PublicacionRepository> {

    private final PublicacionRepository publicacionRepository;
    private final PublicacionNuevaDtoConverter publicacionNuevaDtoConverter;
    private final PublicacionResponseDtoConverter publicacionResponseDtoConverter;
    private final UsuarioRepository usuarioRepository;

    public PublicacionResponseDto guardarPublicacion (PublicacionNuevaDto dto, Usuario usuario){
        Publicacion publicacion=publicacionNuevaDtoConverter.PublicacionNuevaDtoToPublicacion(dto);
        Usuario u=usuarioRepository.findFirstByNick(usuario.getNick()).get();
        publicacion.agregarUsuarioAPublicacion(u);
        return publicacionResponseDtoConverter.publicacionToPublicacionResponseDto(publicacionRepository.save(publicacion));
    }
}
