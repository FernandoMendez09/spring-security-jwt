package com.springmc.security.spring_security.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;

import com.springmc.security.spring_security.model.Response;

/**
 * Clase de utileria para obtener respuestas personalizadas.
 * 
 * @author FernandoMendez
 * @version 1.0.0
 */
public class ResponseUtil {

    /**
     * Método que genera una respuesta correcta generica.
     * 
     * @return response {@link Response} Respuesta.
     */
    public static Response ok( ) {
        return new Response( 0, "Alles ok." );
    }

    /**
     * Método que genera una respuesta correcta generica con un mensaje personalizado.
     * 
     * @param message {@link String} Mensaje personalizado.
     * @return response {@link Response} Respuesta.
     */
    public static Response ok( String message ) {
        return new Response( 0, message );
    }

    /**
     * Método que genera una respuesta de error generica.
     * 
     * @return response {@link Response} Respuesta.
     */
    public static Response error( ) {
        return new Response( 1, "Ha ocurrido un problema." );
    }

    /**
     * Método que genera una respuesta de error generica con un mensaje personalizado.
     * 
     * @param message {@link String} Mensaje personalizado.
     * @return response {@link Response} Respuesta.
     */
    public static Response error( String message ) {
        return new Response( 1, message );
    }

    /**
     * Método que genera una respuesta de entradas invalidas.
     * 
     * @param result {@link BindingResult} Validaciones.
     * @return response {@link Response} Respuesta.
     */
    public static Response invalid( BindingResult result ) {

        StringBuilder errorBuilder = new StringBuilder( );
        Map<String, String> errors = new HashMap<>( );
        result.getFieldErrors( ).forEach( error -> {
            errorBuilder.append( "El campo [" )
                .append( error.getField( ) )
                .append( "] " )
                .append( error.getDefaultMessage( ) );
            errors.put( error.getField( ), errorBuilder.toString( ) );
            errorBuilder.append( "|" );
        } );
        return new Response( 1, errorBuilder.toString( ) );
    }

}