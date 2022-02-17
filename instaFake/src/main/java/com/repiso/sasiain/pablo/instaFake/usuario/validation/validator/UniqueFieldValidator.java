package com.repiso.sasiain.pablo.instaFake.usuario.validation.validator;

import com.repiso.sasiain.pablo.instaFake.usuario.service.auth.UsuarioAuthService;
import com.repiso.sasiain.pablo.instaFake.usuario.validation.anotation.UniqueField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueFieldValidator  implements ConstraintValidator<UniqueField,String> {

    @Autowired
    UsuarioAuthService usuarioAuthService;

    @Override
    public void initialize(UniqueField constraintAnnotation) { }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return usuarioAuthService.findByNick(value).isEmpty();
    }
}
