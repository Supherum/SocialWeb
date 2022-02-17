package com.repiso.sasiain.pablo.instaFake.usuario.service.imp;
import com.repiso.sasiain.pablo.instaFake.error.exceptions.EntityNotFoundExceptionCustom;
import com.repiso.sasiain.pablo.instaFake.error.exceptions.FollowingErrorCustom;
import com.repiso.sasiain.pablo.instaFake.error.exceptions.ForbiddenCustomError;
import com.repiso.sasiain.pablo.instaFake.shared.file.service.FileService;
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
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(UsuarioRegisterDto dto,MultipartFile file) throws IOException {
        String ruta=fileService.saveFile(file);
        Usuario u=usuarioRegisterDtoConverter.usuarioDtoToUsuario(dto, Role.USER);
        u.setFotoPerfil(ruta);
        return usuarioRepository.save(u);
    }

    @Override
    public Usuario edit(UsuarioRegisterDto usuario,MultipartFile file) {
        return null;
    }

    @Override
    public void delete(Usuario usuario) {
    }

    @Override
    public void deleteById(UUID id) {
    }

    private Usuario cargarUsuario (Usuario usuario){
        // aquí cargo un usuario con sus datos concretos sin hacer un fetch demasiado alto
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


}
