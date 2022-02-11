package com.repiso.sasiain.pablo.instaFake.usuario.service;

import com.repiso.sasiain.pablo.instaFake.service.BaseService;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.auth.UsuarioRegisterDto;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.auth.UsuarioRegisterDtoConverter;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Role;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import com.repiso.sasiain.pablo.instaFake.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service("userDetailsService")
@RequiredArgsConstructor
public class AuthService extends BaseService<Usuario, UUID, UsuarioRepository> implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioRegisterDtoConverter useUsuarioRegisterDtoConverter;

    // BUSCA A UN USUARIO POR SU NICK
    @Override
    public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
        return usuarioRepository.findFirstByNick(nick)
                .orElseThrow(()-> new UsernameNotFoundException(nick + " no encontrado"));
    }

    public Optional<Usuario> findByNick(String nick){
        return usuarioRepository.findFirstByNick(nick);
    }


    // Crea un nuevo Usuario
    public Usuario saveUsuario(UsuarioRegisterDto dto, Role rol){
        if(dto.getPassword().equals(dto.getPassword2()) ||
                dto.getApellidos()!=null ||
                dto.getEmail() != null ||
                dto.getNick() != null ||
                dto.getNombre()!=null){
           return save(useUsuarioRegisterDtoConverter.usuarioDtoToUsuario(dto,rol));
        }
        return null;
    }





}
