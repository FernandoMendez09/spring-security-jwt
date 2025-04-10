package com.springmc.security.spring_security.service;

import com.springmc.security.spring_security.enities.User;

/**
 * Clase service para el manejo correcto entre el controlador y el repositorio
 * para el objeto User.
 * 
 * @author FernandoMendez
 * @version 1.0.0
 */
public interface UserService {

    /**
     * Método para llamar al repositorio e insertar un usuario.
     * 
     * @param user {@link User} Objeto a insertar.
     * @return id {@link Long} Identificador del usuario.
     */
    public Long save( User user );

    /**
     * Método para llamar al repositorio y actualizar un usuario.
     * 
     * @param user {@link User} Objeto a actualizar.
     * @return isUpdated {@link Boolean} Sí el usuario fue actualizado.
     */
    public Boolean update( User user );

    /**
     * Método para llamar al repositorio y consultar un usuario para el loggeo.
     * 
     * @param user {@link User} Usuario a buscar y contraseña.
     * @return user {@link User} Usuario loggeado.
     */
    public User login( User user );

}