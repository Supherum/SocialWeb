package com.repiso.sasiain.pablo.instaFake.publicacion.repository;

import com.repiso.sasiain.pablo.instaFake.publicacion.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PublicacionRepository extends JpaRepository<Publicacion, UUID> {

    @Query(value = """
            SELECT * FROM publicacion p
            WHERE p.is_private=FALSE
            """,nativeQuery = true)
    public List<Publicacion> allPublicacionesPublicas ();

    @Query(value = """
            SELECT p FROM Publicacion p JOIN FETCH p.usuario  u
            WHERE  u.nick= :nick
            """)
    public List<Publicacion> allPublicacionesDeUnUsuarioPorNickSeguidor(String nick);

    @Query(value = """
            SELECT p FROM Publicacion p JOIN FETCH p.usuario  u
            WHERE p.isPrivate=false
            AND
            u.nick= :nick
            """)
    public List<Publicacion> allPublicacionesDeUnUsuarioPorNickNoSeguidor(String nick);
}
