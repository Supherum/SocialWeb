package com.repiso.sasiain.pablo.instaFake.usuario.dto;

import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEditDtoConverter {

    public Usuario editarUsuario (UsuarioEditDto dto,Usuario u){
        u.setNombre(dto.getNombre()==null?u.getNombre(): dto.getNombre());
        u.setApellidos(dto.getApellidos()==null?u.getApellidos(): dto.getApellidos());
        u.setEmail(dto.getEmail()==null?u.getEmail():dto.getEmail());
        u.setFechaNaciemiento(dto.getFechaDeNacimiento()==null?u.getFechaNaciemiento():dto.getFechaDeNacimiento());
        u.setPrivado(dto.getIsPrivado()==null?u.isPrivado(): dto.getIsPrivado());
        u.setDireccion(dto.getDireccion()==null?u.getDireccion(): dto.getDireccion());
        u.setTelefono(dto.getTelefono()==null?u.getTelefono(): dto.getTelefono());
        u.setCiudad(dto.getCiudad()==null?u.getCiudad(): dto.getCiudad());
        return u;
    }
}
