package com.repiso.sasiain.pablo.instaFake;

import com.repiso.sasiain.pablo.instaFake.usuario.model.Role;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import com.repiso.sasiain.pablo.instaFake.usuario.repository.UsuarioRepository;
import com.repiso.sasiain.pablo.instaFake.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class initFake {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    
    @PostConstruct
    public void init(){
        Usuario u =Usuario.builder()
                .fotoPerfil("No hay")
                .nick("prueba")
                .role(Role.USER)
                .password("1234")
                .email("email.com")
                .nombre("JUAN")
                .isPrivado(true)
                .listaSeguidores(new ArrayList<>())
                .listaSigo(new ArrayList<>())
                .listaSolicitados(new ArrayList<>())
                .listaSolicitantes(new ArrayList<>())
                .build();

        Usuario u2 =Usuario.builder()
                .fotoPerfil("No hay")
                .nick("prueba2")
                .role(Role.ADMIN)
                .password("67890")
                .email("NoEmail.com")
                .nombre("Paco")
                .isPrivado(false)
                .listaSeguidores(new ArrayList<>())
                .listaSigo(new ArrayList<>())
                .listaSolicitados(new ArrayList<>())
                .listaSolicitantes(new ArrayList<>())
                .build();

        usuarioRepository.save(u);
        usuarioRepository.save(u2);

        u.addSolicitanteToUsuario(u2);
        u.addSolicitanteToUsuario(u);

        usuarioRepository.save(u);

        List<Usuario> usuario2SolicitaLista =usuarioRepository.listaUsuariosQueSolicitoSeguir(u2.getId());
        List<Usuario> usuario1TieneSolicitud=usuarioRepository.listaUsuariosQueMeSolicitanSeguirme(u.getId());

        u.aceptarSolicitudDeUsuario(u2);
        u.denegarSolicitudDeUsuario(u);

        usuarioRepository.save(u);

        List<Usuario> usuariosQueMeSiguen=usuarioRepository.listaUsuariosQueMeSiguen(u.getId());
        List<Usuario> usuariosQueSigo=usuarioRepository.listaUsuariosQueSigo(u2.getId());

        int macedonia= usuarioRepository.numeroDeSolicitudesPendientesDeUnUsuario("prueba");

    }

}
