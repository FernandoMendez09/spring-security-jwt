package com.springmc.security.spring_security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springmc.security.spring_security.enities.Rol;
import java.util.Optional;


/**
 * Interfaz para el manejo de Data JPA del objeto Rol.
 * 
 * @author FernandoMendez
 * @version 1.0.0
 */
@Repository( "rolRepository" )
public interface RolRepository extends CrudRepository<Rol, Long> {

    Optional<Rol> findByName( String name );

}