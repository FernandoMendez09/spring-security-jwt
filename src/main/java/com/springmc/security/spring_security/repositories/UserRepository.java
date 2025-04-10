package com.springmc.security.spring_security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springmc.security.spring_security.enities.User;
import java.util.Optional;


/**
 * Interfaz para el manejo de Data JPA del objeto User.
 * 
 * @author FernandoMendez
 * @version 1.0.0
 */
@Repository( "userRepository" )
public interface UserRepository extends CrudRepository<User, Long> {

    public Optional<User> findByUsername( String username );

}