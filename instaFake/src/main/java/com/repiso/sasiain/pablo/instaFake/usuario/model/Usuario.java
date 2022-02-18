package com.repiso.sasiain.pablo.instaFake.usuario.model;

import com.repiso.sasiain.pablo.instaFake.publicacion.model.Publicacion;
import lombok.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Usuario")
public class Usuario implements Serializable, UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )

    private UUID id;
    private String nombre,apellidos,direccion,email,telefono,ciudad;
    private String fotoPerfil;

    @Builder.Default
    private boolean isPrivado=false;

    @NaturalId
    private String nick;
    private String password;

    @Builder.Default
    private LocalDateTime fechaUltimaPasswordCambiada=LocalDateTime.now();

    @CreatedDate
    @Builder.Default
    private LocalDateTime fechaCreacion=LocalDateTime.now();
    private LocalDate fechaNaciemiento;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "usuario")
    private List<Publicacion> publicacionList=new ArrayList<>();

    @Builder.Default
    @ManyToMany (fetch = FetchType.LAZY)
    private List<Usuario> listaSeguidores=new ArrayList<>();

    @Builder.Default
    @ManyToMany (fetch = FetchType.LAZY,mappedBy = "listaSeguidores")
    private List<Usuario> listaSigo=new ArrayList<>();

    @Builder.Default
    @ManyToMany (fetch = FetchType.LAZY)
    private List<Usuario> listaSolicitantes=new ArrayList<>();

    @Builder.Default
    @ManyToMany (fetch = FetchType.LAZY, mappedBy = "listaSolicitantes")
    private List<Usuario> listaSolicitados=new ArrayList<>();

    @Builder.Default
    @ManyToMany (fetch = FetchType.LAZY)
    private List<Publicacion> publicacionesQueMeGustan=new ArrayList<>();

    // HELPERS

    public void addSolicitanteToUsuario(Usuario usuario){
        this.listaSolicitantes.add(usuario);
        usuario.listaSolicitados.add(this);
    }
    public void deleteSolicitanteToUsuario(Usuario usuario){
        this.listaSolicitantes.remove(usuario);
        usuario.listaSolicitados=usuario.listaSolicitados.stream().filter(x->x.getNick()!=this.nick).collect(Collectors.toList());
    }

    public void addSeguidorToUsuario(Usuario usuario){
        this.listaSeguidores.add(usuario);
        usuario.listaSigo.add(this);
    }
    public void deleteSeguidorToUsuario(Usuario usuario){
        this.listaSeguidores.remove(usuario);
        usuario.listaSigo=usuario.listaSigo.stream().filter(x->x.getNick()!=this.nick).collect(Collectors.toList());
    }


    public void aceptarSolicitudDeUsuario(Usuario usuario){
        this.listaSeguidores.add(usuario);
        this.listaSolicitantes.remove(usuario);

        usuario.listaSolicitados=usuario.listaSolicitados.stream().filter(x->x.getNick()!=this.nick).collect(Collectors.toList());
        usuario.listaSigo.add(this);
    }
    public void denegarSolicitudDeUsuario(Usuario usuario){
        this.listaSolicitantes.remove(usuario);
        usuario.listaSolicitados=usuario.listaSolicitados.stream().filter(x->x.getNick()!=this.nick).collect(Collectors.toList());
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+Role.USER.name()));
    }

    @Override
    public String getUsername() {
        return nick;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
