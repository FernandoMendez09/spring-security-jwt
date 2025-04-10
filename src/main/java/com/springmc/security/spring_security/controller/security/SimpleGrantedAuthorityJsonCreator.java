package com.springmc.security.spring_security.controller.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase marshaller para que el objeto SimpleGrantedAuthority
 * acepte authorities en vez de roles.
 * 
 * @author FernandoMendez
 * @version 1.0.0
 */
public abstract class SimpleGrantedAuthorityJsonCreator {

    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator( @JsonProperty( "authority" ) String role ) { }

}