package com.repiso.sasiain.pablo.instaFake.usuario.validation.anotation;

import com.repiso.sasiain.pablo.instaFake.usuario.validation.validator.UniqueFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueFieldValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueField {


    String message() default "The field need to be Unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
