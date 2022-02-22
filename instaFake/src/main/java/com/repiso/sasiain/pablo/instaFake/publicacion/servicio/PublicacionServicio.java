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
import com.repiso.sasiain.pablo.instaFake.usuario.dto.UserBasicInfoDto;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.UserBasicInfoDtoConverter;
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
    private final UserBasicInfoDtoConverter userBasicInfoDtoConverter;
    private final UsuarioRepository usuarioRepository;
    private final FileService fileService;

    public PublicacionResponseDto guardarNuevaPublicacion (PublicacionNuevaDto dto, Usuario usuario){

        Publicacion publicacion=publicacionNuevaDtoConverter.PublicacionNuevaDtoToPublicacion(dto);
        Usuario u=usuarioRepository.findFirstByNick(usuario.getNick()).get();
        publicacion.agregarUsuarioAPublicacion(u);
        return publicacionResponseDtoConverter.publicacionToPublicacionResponseDto(publicacionRepository.save(publicacion),userBasicInfoDtoConverter.userToUserBasicInfoDto(usuario));
    }

    public PublicacionResponseDto editarPublicacion (UUID id, PublicacionNuevaDto dto,Usuario usuario) throws IOException {
        Optional<Publicacion> publicacionOpt =publicacionRepository.findById(id);
        if(publicacionOpt.isPresent() && comprobarPropietarioDePublicacionOAdmin(usuario,publicacionOpt.get())){
            delteResourcesToPublicacion(publicacionOpt.get());
            Publicacion publicacionEditada=publicacionNuevaDtoConverter.publicacionNuevaDtoEditPublicacion(dto,publicacionOpt.get());
            return publicacionResponseDtoConverter.publicacionToPublicacionResponseDto(publicacionRepository.save(publicacionEditada),userBasicInfoDtoConverter.userToUserBasicInfoDto(usuario));
        }
        throw new EntityNotFoundExceptionCustom(EntityNotFoundExceptionCustom.class);
    }

    public void deletePublicacion (UUID id,Usuario usuario) throws IOException {

        Optional<Publicacion> publicacionOpt =publicacionRepository.findById(id);
        if(publicacionOpt.isPresent() && comprobarPropietarioDePublicacionOAdmin(usuario,publicacionOpt.get())){
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
            return publicacionResponseDtoConverter.publicacionToPublicacionResponseDto(publicacionOpt.get(),userBasicInfoDtoConverter.userToUserBasicInfoDto(usuarioDeLaPublicacion));
        }
        throw new EntityNotFoundExceptionCustom(Publicacion.class);
    }

    public List<PublicacionResponseDto> getAllPublicaciones (){
        List<Publicacion> lista=publicacionRepository.allPublicacionesPublicas();
        List<PublicacionResponseDto> listaDto=lista.stream().map(x->publicacionResponseDtoConverter.publicacionToPublicacionResponseDto(x,usuarioRepository.nickDeUnaPublicacion(x.getId()))).collect(Collectors.toList());
        return listaDto;
    }

    public List<PublicacionResponseDto> getPublicacionesUsuarioPorNick(String nick,Usuario usuario){
        Optional<Usuario> usuarioOpt = usuarioRepository.findFirstByNick(nick);
        if (usuarioOpt.isPresent()){

            if(comprobarSiUnUsuarioMeSigue(usuarioOpt.get(),usuario)){
                List<Publicacion> lista=publicacionRepository.allPublicacionesDeUnUsuarioPorNickSeguidor(nick);
                List<PublicacionResponseDto> listaDto=lista.stream().map(x->publicacionResponseDtoConverter.publicacionToPublicacionResponseDto(x,userBasicInfoDtoConverter.userToUserBasicInfoDto(usuarioOpt.get()))).collect(Collectors.toList());
                return listaDto;
            }
            throw new EntityNotFoundExceptionCustom(Publicacion.class);
        }
       throw new EntityNotFoundExceptionCustom(Usuario.class);
    }

    public PublicacionResponseDto addResourceToPublicacion(MultipartFile file,UUID id,Usuario usuario) throws IOException, VideoException {
        Optional<Publicacion> pubOpt=publicacionRepository.findById(id);
        if(pubOpt.isPresent() && comprobarPropietarioDePublicacionOAdmin(usuario,pubOpt.get())){
            List<String> fileNames=fileService.saveFileWithCopy(file,1024);
            fileNames=fileNames.stream().map(x->fileService.getUri(x)).collect(Collectors.toList());
            fileNames.stream().map(x->pubOpt.get().getListrecurso().add(x));
            publicacionRepository.save(pubOpt.get());
            return publicacionResponseDtoConverter.publicacionToPublicacionResponseDto(pubOpt.get(),userBasicInfoDtoConverter.userToUserBasicInfoDto(usuario));
        }
        throw new EntityNotFoundExceptionCustom(Publicacion.class);
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

    private boolean comprobarPropietarioDePublicacionOAdmin(Usuario usuario,Publicacion publicacion){
        Usuario usuarioDeLaPublicacion=usuarioRepository.findUserByPublicacionId(publicacion.getId());
        return usuario.getId().toString().equals(usuarioDeLaPublicacion.getId().toString()) || usuario.getRole()== Role.ADMIN;

    }

    private boolean comprobarSiUnUsuarioMeSigue (Usuario usuarioDeLaPublicacion,Usuario usuarioAComprobar){
        List<Usuario> listaSeguidoresUsuario=usuarioRepository.listaUsuariosQueMeSiguen(usuarioDeLaPublicacion.getId());
        listaSeguidoresUsuario=listaSeguidoresUsuario.stream().filter(x->x.getNick().equals(usuarioAComprobar.getNick())).collect(Collectors.toList());
        return !listaSeguidoresUsuario.isEmpty() || usuarioAComprobar.getNick().equals(usuarioDeLaPublicacion.getNick());
    }
}
