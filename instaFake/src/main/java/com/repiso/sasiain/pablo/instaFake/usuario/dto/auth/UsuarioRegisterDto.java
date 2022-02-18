package com.repiso.sasiain.pablo.instaFake.usuario.dto.auth;

import com.repiso.sasiain.pablo.instaFake.usuario.validation.anotation.SamePassword;
import com.repiso.sasiain.pablo.instaFake.usuario.validation.anotation.UniqueField;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Builder
@Getter @Setter
@SamePassword(password1 = "password", password2 = "password2")
public class UsuarioRegisterDto {

    @UniqueField (message = "{nick.name.unique}")
    private String nick;
    @NotEmpty(message = "{field.can.not.be.null}")
    private String nombre;
    @NotEmpty(message = "{field.can.not.be.null}")
    private String apellidos;
    @Email(message = "{email.format}")
    private String email;
    private String password;
    private String password2;
    private String uri;
    private boolean isPrivado;
    private String direccion,telefono,ciudad;
    @Past (message = "{birth.date.must.past}")
    private LocalDate fechaDeNacimiento;

}
