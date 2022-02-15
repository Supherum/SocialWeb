package com.repiso.sasiain.pablo.instaFake.publicacion.repository;

import com.repiso.sasiain.pablo.instaFake.publicacion.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PublicacionRepository extends JpaRepository<Publicacion, UUID> {

}
