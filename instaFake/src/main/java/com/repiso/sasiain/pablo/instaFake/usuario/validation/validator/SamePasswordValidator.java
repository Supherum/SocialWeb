package com.repiso.sasiain.pablo.instaFake.usuario.validation.validator;

import com.repiso.sasiain.pablo.instaFake.usuario.validation.anotation.SamePassword;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SamePasswordValidator implements ConstraintValidator<SamePassword,Object> {

    private String password1;
    private String password2;

    @Override
    public void initialize(SamePassword constraintAnnotation) {
        this.password1= constraintAnnotation.password1();
        this.password2= constraintAnnotation.password2();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object password1Value = PropertyAccessorFactory.forBeanPropertyAccess(value).getPropertyValue(password1);
        Object password2Value = PropertyAccessorFactory.forBeanPropertyAccess(value).getPropertyValue(password2);
        if(password1Value==null || password2Value==null)
            return false;
        return password1Value.equals(password2Value);
    }
}
