package com.springmc.security.spring_security.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.springmc.security.spring_security.enities.Product;

/**
 * Clase para la validación personalizada de un objeto.
 * 
 * @author FernandoMendez
 * @version 1.0.0
 */
@Component
public class ProductValidation implements Validator {

    @Override
    public boolean supports( Class<?> clazz ) {
        return Product.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors ) {
        Product product = ( Product ) target;
        // ValidationUtils.rejectIfEmptyOrWhitespace( errors, "name", "message.not.blank" );
        if( product.getName( ) == null || product.getName( ).trim( ).isEmpty( ) )
            errors.rejectValue( "name", "message.not.blank" );
    }

}