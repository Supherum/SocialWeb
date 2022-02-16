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

@Service
@RequiredArgsConstructor
public class UsuarioServiceImp implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioRegisterDtoConverter usuarioRegisterDtoConverter;
    private final FileService fileService;


    @Override
    public String seguirUsuario(String nick, Usuario usuario) {
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
            return null;
        }
        throw new EntityNotFoundExceptionCustom(Usuario.class);
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
}
