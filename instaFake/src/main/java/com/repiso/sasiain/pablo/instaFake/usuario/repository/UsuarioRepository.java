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
            SELECT * FROM usuario u JOIN publicacion p 
            WHERE usuario_id=:id
            """,nativeQuery = true)
    public Usuario findPublicationUserId (UUID id);
}
