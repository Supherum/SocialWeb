package com.repiso.sasiain.pablo.instaFake.usuario.controller;

import com.repiso.sasiain.pablo.instaFake.security.jwt.JwtManager;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.auth.UsuarioLoginDtoResponse;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.auth.UsuarioLoginDtoResponseConverter;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.auth.UsuarioRegisterDto;
import com.repiso.sasiain.pablo.instaFake.usuario.dto.auth.UsuarioRegisterDtoConverter;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Role;
import com.repiso.sasiain.pablo.instaFake.usuario.model.Usuario;
import com.repiso.sasiain.pablo.instaFake.usuario.service.UsuarioAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class UsuarioRegisterController {

    private final UsuarioAuthService usuarioAuthService;
    private final UsuarioLoginDtoResponseConverter usuarioLoginDtoResponseConverter;
    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;


    @PostMapping("/usuario")
    public ResponseEntity<UsuarioLoginDtoResponse> registerAndLogin(@Valid @RequestBody UsuarioRegisterDto dto) {
        Usuario u= usuarioAuthService.saveUsuario(dto,Role.USER);
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                u.getNick(),
                                dto.getPassword()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtManager.generateToken(authentication);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioLoginDtoResponseConverter.UserAndTokenToUsuarioDtoResponse(usuario,jwt));
    }



}
