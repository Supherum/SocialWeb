package com.repiso.sasiain.pablo.instaFake.publicacion.model;

import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private String title,description;
    @CreatedDate
    private LocalDateTime createdTime;
    @ManyToOne
    private Usuario usuario;
    @Builder.Default
    @ElementCollection
    private List<String> resourceList=new ArrayList<>();
}
