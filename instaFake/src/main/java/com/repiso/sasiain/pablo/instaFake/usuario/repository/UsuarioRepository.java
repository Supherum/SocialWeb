package com.repiso.sasiain.pablo.instaFake.usuario.repository;

import com.repiso.sasiain.pablo.instaFake.publicacion.model.Publicacion;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Role;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findFirstByNick(String nick);

    List<Usuario> findByRole (Role role);


    @Query(value = """
            SELECT u FROM Usuario u JOIN FETCH u.publicacionList p 
            WHERE p.id= :id
            """)
    public Usuario findUserByPublicacionId (UUID id);


    // Con el usuario de la publicacion y el logeado compruebo que el logueado le sigue
    @Query(value = """
            SELECT u FROM Usuario u JOIN FETCH u.listaSeguidores ud
            WHERE ud.id = :secondId
            AND u.id = :firstId
            """)
    public List<Usuario> isfirstUserFollowingSecondUser (UUID firstId,UUID secondId);



    @Query(value = """
            SELECT s FROM Usuario u JOIN u.listaSolicitantes s
            WHERE u.id = :id
            """)
    public List<Usuario> listaUsuariosQueMeSolicitanSeguirme (UUID id);

    @Query(value = """
            SELECT s FROM Usuario u JOIN u.listaSolicitados s
            WHERE u.id = :id
            """)
    public List<Usuario> listaUsuariosQueSolicitoSeguir(UUID id);
    @Query(value = """
            SELECT s FROM Usuario u JOIN u.listaSigo s
            WHERE u.id = :id
            """)
    public List<Usuario> listaUsuariosQueSigo(UUID id);
    @Query(value = """
            SELECT s FROM Usuario u JOIN u.listaSeguidores s
            WHERE u.id = :id
            """)
    public List<Usuario> listaUsuariosQueMeSiguen(UUID id);


}
