package com.repiso.sasiain.pablo.instaFake.usuario.dto.auth;

import com.repiso.sasiain.pablo.instaFake.usuario.validation.anotation.SamePassword;
import com.repiso.sasiain.pablo.instaFake.usuario.validation.anotation.UniqueField;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Builder
@Getter @Setter
@SamePassword(password1 = "password", password2 = "password2")
public class UsuarioRegisterDto {

    @UniqueField
    private String nick;
    private String nombre;
    private String apellidos;
    @Email
    private String email;
    private LocalDate fechaNacimiento;
    private String password;
    private String password2;
    private String uri;
    private boolean isPrivado;
    private String direccion,telefono,ciudad;
    private LocalDate fechaDeNacimiento;

}
