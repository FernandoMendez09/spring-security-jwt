package com.springmc.security.spring_security.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springmc.security.spring_security.enities.Product;
import com.springmc.security.spring_security.enities.User;
import com.springmc.security.spring_security.repositories.UserRepository;

/**
 * Clase para la validación personalizada de un username.
 * 
 * @author FernandoMendez
 * @version 1.0.0
 */
@Component
public class UsernameValidation implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports( Class<?> clazz ) {
        return User.class.isAssignableFrom( clazz );
    }

    @Transactional( readOnly = true )
    @Override
    public void validate( Object target, Errors errors ) {
        User user = ( User ) target;
        userRepository.findByUsername( user.getUsername( ) ).ifPresent( returnedUser -> {
            if( returnedUser.getStatus( ) == false )
                errors.rejectValue( "username", null, "pertenece a un usuario que no se encuentra activo." );
        } );
    }

}
