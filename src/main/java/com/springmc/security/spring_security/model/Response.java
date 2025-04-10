package com.springmc.security.spring_security.model;

import lombok.Data;

/**
 * Clase respuesta que devolverá el servicio.
 * 
 * @author FernandoMendez
 * @version 1.0.0
 */
@Data
public class Response {

    private int code;
    private String message;
    private Object data;

    public Response( ) { }

    public Response( int code, String message ) {
        this.code = code;
        this.message = message;
    }

}