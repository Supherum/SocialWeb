package com.repiso.sasiain.pablo.instaFake.usuario.validation.anotation;


import com.repiso.sasiain.pablo.instaFake.usuario.validation.validator.SamePasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SamePasswordValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SamePassword {

    String message () default  "Passwords need be the same and not null";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String password1();
    String password2();
}
