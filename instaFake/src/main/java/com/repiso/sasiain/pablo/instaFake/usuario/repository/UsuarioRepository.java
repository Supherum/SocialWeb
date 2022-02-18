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


    @Query(value = """
            SELECT DISTINCT(NICK) FROM USUARIO
            JOIN USUARIO_LISTA_SOLICITANTES
            WHERE ID=LISTA_SOLICITANTES_ID           
            """,nativeQuery = true)
    public List<String> listaDeNickDeUsuaiosTienenSolicitudesPendientes ();

    @Query(value = """
            SELECT COUNT(*) FROM USUARIO
            JOIN USUARIO_LISTA_SOLICITANTES
            WHERE ID=LISTA_SOLICITANTES_ID
            AND NICK=:nick           
            """,nativeQuery = true)
    public int numeroDeSolicitudesPendientesDeUnUsuario (String nick);

    @Query(value = """
            SELECT COUNT(*) FROM USUARIO
            JOIN USUARIO_LISTA_SEGUIDORES
            WHERE LISTA_SEGUIDORES_ID=ID 
            AND NICK= :nick         
            """,nativeQuery = true)
    public int numeroDeSeguidores(String nick);



}
