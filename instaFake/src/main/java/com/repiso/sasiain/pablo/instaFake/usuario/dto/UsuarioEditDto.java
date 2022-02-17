package com.repiso.sasiain.pablo.instaFake.usuario.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Getter @Setter
@Builder
public class UsuarioEditDto {

    private String nombre;
    private String apellidos;
    @Email
    private String email;
    private Boolean isPrivado;
    @Builder.Default
    private String direccion="",telefono="",ciudad="";
    private LocalDate fechaDeNacimiento;

}
