package com.repiso.sasiain.pablo.instaFake.error.exceptions;

public class FileEmptyExceptionCustom extends RuntimeException{
    public FileEmptyExceptionCustom(Class clas){
        super (String.format("Fichero vacio: "+ clas.getName()));
    }
}
