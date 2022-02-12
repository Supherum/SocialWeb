package com.repiso.sasiain.pablo.instaFake.error.exceptions;

public class EntityNotFoundExceptionCustom extends RuntimeException{
    public EntityNotFoundExceptionCustom(Class clas){
        super (String.format("No se ha encontrado el objeto: "+ clas.getName()));
    }
}
