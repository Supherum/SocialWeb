package com.repiso.sasiain.pablo.instaFake.publicacion.servicio;

import com.repiso.sasiain.pablo.instaFake.error.exceptions.EntityNotFoundExceptionCustom;
import com.repiso.sasiain.pablo.instaFake.error.exceptions.ForbiddenCustomError;
import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionNuevaDto;
import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionNuevaDtoConverter;
import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionResponseDto;
import com.repiso.sasiain.pablo.instaFake.publicacion.dto.PublicacionResponseDtoConverter;
import com.repiso.sasiain.pablo.instaFake.publicacion.model.Publicacion;
import com.repiso.sasiain.pablo.instaFake.publicacion.repository.PublicacionRepository;
import com.repiso.sasiain.pablo.instaFake.shared.file.service.FileService;
import com.repiso.sasiain.pablo.instaFake.shared.service.BaseService;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Role;
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
            comprobarPropietarioDePublicacionOAdmin(usuario,publicacionOpt.get());
            delteResourcesToPublicacion(publicacionOpt.get());
            Publicacion publicacionEditada=publicacionNuevaDtoConverter.publicacionNuevaDtoEditPublicacion(dto,publicacionOpt.get());
            return publicacionResponseDtoConverter.publicacionToPublicacionResponseDto(publicacionRepository.save(publicacionEditada));
        }
        throw new EntityNotFoundExceptionCustom(EntityNotFoundExceptionCustom.class);
    }

    public void deletePublicacion (UUID id,Usuario usuario) throws IOException {

        Optional<Publicacion> publicacionOpt =publicacionRepository.findById(id);
        if(publicacionOpt.isPresent()){
            comprobarPropietarioDePublicacionOAdmin(usuario,publicacionOpt.get());
            delteResourcesToPublicacion(publicacionOpt.get());
            repository.delete(publicacionOpt.get());
        }
        else throw new EntityNotFoundExceptionCustom(EntityNotFoundExceptionCustom.class);
    }

    public PublicacionResponseDto getPublicacion (UUID id,Usuario usuario){
        Optional<Publicacion> publicacionOpt = publicacionRepository.findById(id);
        if(publicacionOpt.isPresent()){
            Usuario usuarioDeLaPublicacion=usuarioRepository.findUserByPublicacionId(publicacionOpt.get().getId());
            boolean esSeguidor=comprobarSiUnUsuarioMeSigue(usuarioDeLaPublicacion,usuario);
            if (usuario.getRole()== Role.ADMIN || !publicacionOpt.get().isPrivate() || esSeguidor)
            return publicacionResponseDtoConverter.publicacionToPublicacionResponseDto(publicacionOpt.get());
        }
        throw new EntityNotFoundExceptionCustom(Publicacion.class);
    }

    public List<PublicacionResponseDto> getAllPublicaciones (){
        List<Publicacion> lista=publicacionRepository.allPublicacionesPublicas();
        List<PublicacionResponseDto> listaDto=lista.stream().map(publicacionResponseDtoConverter::publicacionToPublicacionResponseDto).collect(Collectors.toList());
        return listaDto;
    }

    public List<PublicacionResponseDto> getPublicacionesUsuarioPorNick(String nick,Usuario usuario){
        List<Publicacion> lista=publicacionRepository.allPublicacionesDeUnUsuarioPorNickSeguidor(nick);
        List<PublicacionResponseDto> listaDto=lista.stream().map(publicacionResponseDtoConverter::publicacionToPublicacionResponseDto).collect(Collectors.toList());
        return listaDto;
    }



    // UTILS FUNCTIONS

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

    private void comprobarPropietarioDePublicacionOAdmin(Usuario usuario,Publicacion publicacion){
        Usuario usuarioDeLaPublicacion=usuarioRepository.findUserByPublicacionId(publicacion.getId());
        if(!usuario.getId().toString().equals(usuarioDeLaPublicacion.getId().toString()) && usuario.getRole()!= Role.ADMIN)
            throw new ForbiddenCustomError(Publicacion.class);
    }

    private boolean comprobarSiUnUsuarioMeSigue (Usuario usuarioDeLaPublicacion,Usuario usuarioAComprobar){
        List<Usuario> listaSeguidoresUsuario=usuarioRepository.listaUsuariosQueMeSiguen(usuarioDeLaPublicacion.getId());
        listaSeguidoresUsuario=listaSeguidoresUsuario.stream().filter(x->x.getNick().equals(usuarioAComprobar.getNick())).collect(Collectors.toList());
        return !listaSeguidoresUsuario.isEmpty() || usuarioAComprobar.getNick().equals(usuarioDeLaPublicacion.getNick());
    }
}
