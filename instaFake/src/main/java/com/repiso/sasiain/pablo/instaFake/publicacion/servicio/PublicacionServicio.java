package com.repiso.sasiain.pablo.instaFake.publicacion.servicio;

import com.repiso.sasiain.pablo.instaFake.error.exceptions.EntityNotFoundExceptionCustom;
import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionNuevaDto;
import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionNuevaDtoConverter;
import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionResponseDto;
import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionResponseDtoConverter;
import com.repiso.sasiain.pablo.instaFake.publicacion.model.Publicacion;
import com.repiso.sasiain.pablo.instaFake.publicacion.repository.PublicacionRepository;
import com.repiso.sasiain.pablo.instaFake.shared.file.service.FileService;
import com.repiso.sasiain.pablo.instaFake.shared.service.BaseService;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import com.repiso.sasiain.pablo.instaFake.usuario.repository.UsuarioRepository;
import io.github.techgnious.exception.VideoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@Service
@RequiredArgsConstructor
public class PublicacionServicio extends BaseService<Publicacion, UUID, PublicacionRepository> {

    private final PublicacionRepository publicacionRepository;
    private final PublicacionNuevaDtoConverter publicacionNuevaDtoConverter;
    private final PublicacionResponseDtoConverter publicacionResponseDtoConverter;
    private final UsuarioRepository usuarioRepository;
    private final FileService fileService;

    public PublicacionResponseDto guardarNuevaPublicacion (PublicacionNuevaDto dto, Usuario usuario){

        Publicacion publicacion=publicacionNuevaDtoConverter.PublicacionNuevaDtoToPublicacion(dto);
        Usuario u=usuarioRepository.findFirstByNick(usuario.getNick()).get();
        publicacion.agregarUsuarioAPublicacion(u);
        return publicacionResponseDtoConverter.publicacionToPublicacionResponseDto(publicacionRepository.save(publicacion));
    }

    public PublicacionResponseDto editarPublicacion (UUID id, PublicacionNuevaDto dto,Usuario usuario) throws IOException {
        Optional<Publicacion> publicacionOpt =publicacionRepository.findById(id);
        if(publicacionOpt.isPresent()){
            Usuario p=usuarioRepository.findPublicationUserId(publicacionOpt.get().getId());
            p.getId();
            delteResourcesToPublicacion(publicacionOpt.get());
            Publicacion publicacionEditada=publicacionNuevaDtoConverter.publicacionNuevaDtoEditPublicacion(dto,publicacionOpt.get());
            return publicacionResponseDtoConverter.publicacionToPublicacionResponseDto(publicacionRepository.save(publicacionEditada));
        }
        throw new EntityNotFoundExceptionCustom(EntityNotFoundExceptionCustom.class);
    }

    public void deletePublicacion (UUID id,Usuario usuario) throws IOException {

        Optional<Publicacion> publicacionOpt =publicacionRepository.findById(id);
        if(publicacionOpt.isPresent()){
            delteResourcesToPublicacion(publicacionOpt.get());
            repository.delete(publicacionOpt.get());
        }
        else throw new EntityNotFoundExceptionCustom(EntityNotFoundExceptionCustom.class);
    }

    public PublicacionNuevaDto addResourceToDto (MultipartFile file,PublicacionNuevaDto dto) throws IOException, VideoException {

            List<String> nombreArchivos=fileService.saveFileWithCopy(file,1024);
            String uri1 =fileService.getUri(nombreArchivos.get(0));
            String uri2 =fileService.getUri(nombreArchivos.get(1));
            dto.setResource(new ArrayList<>());
            dto.getResource().add(uri1);
            dto.getResource().add(uri2);
            return dto;
    }

    private void delteResourcesToPublicacion(Publicacion publicacion) throws IOException {
        List<String> listaLinks=publicacion.getListrecurso();
        listaLinks=listaLinks.stream().map(x->fileService.getFileNameOnUrl(x)).collect(Collectors.toList());
        fileService.deleteListFile(listaLinks);
    }



}
