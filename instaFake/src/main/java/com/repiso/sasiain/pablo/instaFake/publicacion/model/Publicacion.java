package com.repiso.sasiain.pablo.instaFake.publicacion.model;

import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Publicacion {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    private UUID id;
    private String titulo,descripcion;
    private boolean isPrivate;
    @CreatedDate

    @Builder.Default
    private LocalDateTime createdTime=LocalDateTime.now();

    @ManyToOne
    private Usuario usuario;

    private String recurso;

    public void agregarUsuarioAPublicacion(Usuario u){
        this.usuario=u;
        u.getPublicacionList().add(this);
    }

    public void quitarUsuarioAPublicacion(Usuario u){
        this.usuario=null;
        u.getPublicacionList().remove(this);
    }
}
