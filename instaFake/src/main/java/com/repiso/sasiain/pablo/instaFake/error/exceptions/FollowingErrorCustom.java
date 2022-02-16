package com.repiso.sasiain.pablo.instaFake.error.exceptions;

public class FollowingErrorCustom extends RuntimeException{

    public FollowingErrorCustom(){
        super (String.format("Error al intentar seguir a alguien"));
    }
}
