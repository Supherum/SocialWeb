package com.repiso.sasiain.pablo.instaFake.usuario.service.imp;
import com.repiso.sasiain.pablo.instaFake.error.exceptions.EntityNotFoundExceptionCustom;
import com.repiso.sasiain.pablo.instaFake.error.exceptions.FollowingErrorCustom;
import com.repiso.sasiain.pablo.instaFake.error.exceptions.ForbiddenCustomError;
import com.repiso.sasiain.pablo.instaFake.publicacion.model.Publicacion;
import com.repiso.sasiain.pablo.instaFake.publicacion.repository.PublicacionRepository;
import com.repiso.sasiain.pablo.instaFake.shared.file.service.FileService;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.*;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.auth.UsuarioRegisterDtoConverter;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.auth.UsuarioRegisterDto;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import com.repiso.sasiain.pablo.instaFake.usuario.repository.UsuarioRepository;
import com.repiso.sasiain.pablo.instaFake.usuario.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImp implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioRegisterDtoConverter usuarioRegisterDtoConverter;
    private final FileService fileService;
    private final UsuarioEditDtoConverter usuarioEditDtoConverter;
    private final PublicacionRepository publicacionRepository;
    private final UserBasicInfoDtoConverter userBasicInfoDtoConverter;

    @Override
    public String solicitarSeguirUsuario(String nick, Usuario usuario) {
        Optional<Usuario> usuarioAlQuieroSeguir=usuarioRepository.findFirstByNick(nick);
        if(usuarioAlQuieroSeguir.isPresent()){
            if(usuarioAlQuieroSeguir.get().getId().equals(usuario.getId())){
                throw new FollowingErrorCustom();
            }
            List<Usuario> listaSolicitantes= usuarioRepository.listaUsuariosQueMeSolicitanSeguirme(usuarioAlQuieroSeguir.get().getId());
            List<Usuario> listaSolicitoSeguir=usuarioRepository.listaUsuariosQueSolicitoSeguir(usuario.getId());
            usuarioAlQuieroSeguir.get().setListaSolicitantes(listaSolicitantes);
            usuario.setListaSolicitados(listaSolicitoSeguir);
            usuarioAlQuieroSeguir.get().addSolicitanteToUsuario(usuario);
            usuarioRepository.save(usuarioAlQuieroSeguir.get());
            return "Ya has enviado la petición al usuario: "+nick;
        }
        throw new EntityNotFoundExceptionCustom(Usuario.class);
    }

    @Override
    public String aceptarSolicitudUsuario(Usuario usuario, UUID id) {
        Optional<Usuario> usuarioQueQuieroAceptarOpt=usuarioRepository.findById(id);
        if (usuarioQueQuieroAceptarOpt.isPresent()){
            Usuario usuarioQueQuieroAceptar=usuarioQueQuieroAceptarOpt.get();

            usuario=cargarUsuario(usuario);
            usuarioQueQuieroAceptar=cargarUsuario(usuarioQueQuieroAceptar);

            comprobarSiEstaElUsuarioQueQuieroAceptar(usuario,usuarioQueQuieroAceptar.getNick());

            usuario.aceptarSolicitudDeUsuario(usuarioQueQuieroAceptar);
            usuarioRepository.save(usuario);
            return "Has aceptado al usuario: "+usuarioQueQuieroAceptar.getNick();

        }
        throw new EntityNotFoundExceptionCustom(Usuario.class);
    }

    public String declinarSolicitudUsuario(Usuario usuario,UUID id){
        Optional<Usuario> usuarioQueQuieroDeclinarOpt=usuarioRepository.findById(id);
        if(usuarioQueQuieroDeclinarOpt.isPresent()){
            Usuario usuarioQueQuieroDeclinar=usuarioQueQuieroDeclinarOpt.get();

            usuarioQueQuieroDeclinar=cargarUsuario(usuarioQueQuieroDeclinar);
            usuario=cargarUsuario(usuario);

            usuario.denegarSolicitudDeUsuario(usuarioQueQuieroDeclinar);
            usuarioRepository.save(usuario);
            return "Has rechazado la petición de: "+usuarioQueQuieroDeclinar.getNick();
        }
        throw  new EntityNotFoundExceptionCustom(Usuario.class);
    }

    @Override
    public List<SolicitudesDtoResponse> listaDePeticionesPendientes() {
        List<String> listaUsuariosQueSolicitan=usuarioRepository.listaDeNickDeUsuaiosTienenSolicitudesPendientes();
        List<SolicitudesDtoResponse>listaSolicitudesResponse= listaUsuariosQueSolicitan.stream().map(x->solicitudesDtoCreatorResponse(x)).collect(Collectors.toList());
        return listaSolicitudesResponse;
    }

    @Override
    public List<UserBasicInfoDto> miListaDeSolicitudes(Usuario usuario) {
        return usuarioRepository.listaUsuariosQueMeSolicitanSeguirme(usuario.getId()).stream().map(userBasicInfoDtoConverter::userToUserBasicInfoDto).collect(Collectors.toList());
    }

    @Override
    public List<UserBasicInfoDto> miListaDeSeguidores(Usuario usuario) {
        return usuarioRepository.listaUsuariosQueMeSiguen(usuario.getId()).stream().map(userBasicInfoDtoConverter::userToUserBasicInfoDto).collect(Collectors.toList());
    }

    @Override
    public List<UserBasicInfoDto> miListaDeSeguidos(Usuario usuario) {
        return usuarioRepository.listaUsuariosQueMeSiguen(usuario.getId()).stream().map(userBasicInfoDtoConverter::userToUserBasicInfoDto).collect(Collectors.toList());
    }

    @Override
    public UsuarioPerfilResponse perfilDeUsuario(Usuario usuario,UUID id) {
        Optional<Usuario> usuarioQueQuieroVerOpt=usuarioRepository.findById(id);
        if(usuarioQueQuieroVerOpt.isPresent()) {
            Usuario usuarioQueQuierover=usuarioQueQuieroVerOpt.get();
            if (esMiSeguidorOPublico(usuario, usuarioQueQuieroVerOpt.get())) {

                return UsuarioPerfilResponse.builder()
                        .nick(usuarioQueQuierover.getNick())
                        .id(usuarioQueQuierover.getId())
                        .nombre(usuarioQueQuierover.getNombre())
                        .apellidos(usuarioQueQuierover.getApellidos())
                        .email(usuarioQueQuierover.getEmail())
                        .fotoPerfil(usuarioQueQuierover.getFotoPerfil())
                        .fechaNaciemiento(usuarioQueQuierover.getFechaNaciemiento())
                        .seguidores(usuarioRepository.numeroDeSeguidores(usuarioQueQuierover.getNick()))
                        .build();
            }
            throw new EntityNotFoundExceptionCustom(Usuario.class);
        }
        throw new EntityNotFoundExceptionCustom(Usuario.class);
    }


    @Override
    public Usuario save(UsuarioRegisterDto dto,MultipartFile file) throws IOException {
        String fileName=fileService.generateName(file);
        String ruta=fileService.saveFile(file,fileName);
        Usuario u=usuarioRegisterDtoConverter.usuarioDtoToUsuario(dto, Role.USER);
        u.setFotoPerfil(ruta);
        return usuarioRepository.save(u);
    }

    @Override
    public Usuario save(UsuarioEditDto dto, MultipartFile file, Usuario usuario) throws IOException {
        fileService.deleteFile(fileService.getFileNameOnUrl(usuario.getFotoPerfil()));
        String nombreArchivo=fileService.rescaleAndSaveImagen(file,124);
        String uri = fileService.getUri(nombreArchivo);
        Usuario u=usuarioEditDtoConverter.editarUsuario(dto,usuario);
        u.setFotoPerfil(uri);
        u.setId(usuario.getId());
        return usuarioRepository.save(u);
    }


    public String likePublicacion (Usuario usuario,UUID id){
        Optional<Publicacion> pubOpt=publicacionRepository.findById(id);
        if (pubOpt.isPresent()){
            Usuario propietarioPublicacion=usuarioRepository.findUserByPublicacionId(pubOpt.get().getId());
            esMiSeguidorOPublico(usuario,propietarioPublicacion);
            List<Publicacion> listaPublicacionesQueMeGustan=publicacionRepository.listaDeFavoritosDeUnUsuario(usuario.getNick());
            listaPublicacionesQueMeGustan.add(pubOpt.get());
            usuario.setPublicacionesQueMeGustan(listaPublicacionesQueMeGustan);
            usuarioRepository.save(usuario);
            return "Has añadido la publicación "+pubOpt.get().getId()+" a tu lista de Favs";
        }
        throw new EntityNotFoundExceptionCustom(Publicacion.class);


    }

    // UTILS

    private Usuario cargarUsuario (Usuario usuario){
        // aquí cargo un usuario con sus datos concretos sin hacer un fetch demasiado alto pero con 4 consultas
        // TODO hacerlo en una sola consulta que devuelva un DTO gigante con todo
        List<Usuario> solicitantes = usuarioRepository.listaUsuariosQueMeSolicitanSeguirme(usuario.getId());
        List<Usuario> solicito=usuarioRepository.listaUsuariosQueSolicitoSeguir(usuario.getId());
        List<Usuario> sigo=usuarioRepository.listaUsuariosQueSigo(usuario.getId());
        List<Usuario> meSiguen=usuarioRepository.listaUsuariosQueMeSiguen(usuario.getId());

        usuario.setListaSeguidores(meSiguen);
        usuario.setListaSolicitantes(solicitantes);
        usuario.setListaSigo(sigo);
        usuario.setListaSolicitados(solicito);
        return usuario;
    }

    private void comprobarSiEstaElUsuarioQueQuieroAceptar(Usuario usuario,String nick){
        List<Usuario> solicitantes=usuario.getListaSolicitantes();
        solicitantes=solicitantes.stream().filter(x->x.getNick().equals(nick)).collect(Collectors.toList());
        if(solicitantes.isEmpty())
            throw new FollowingErrorCustom();
    }

    private SolicitudesDtoResponse solicitudesDtoCreatorResponse (String nick){
        int numero=usuarioRepository.numeroDeSolicitudesPendientesDeUnUsuario(nick);
        return SolicitudesDtoResponse.builder()
                .nick(nick)
                .numeroPeticionesPendientes(numero)
                .build();
    }

    private boolean esMiSeguidorOPublico(Usuario usuarioLogieado, Usuario usuarioQuiereVer){
            List<Usuario> meSiguen=usuarioRepository.listaUsuariosQueMeSiguen(usuarioQuiereVer.getId());
            meSiguen=meSiguen.stream().filter(x->x.getNick().equals(usuarioLogieado.getNick())).collect(Collectors.toList());
            return !meSiguen.isEmpty() || usuarioLogieado.getNick().equals(usuarioQuiereVer.getNick()) || !usuarioQuiereVer.isPrivado();
    }


}
