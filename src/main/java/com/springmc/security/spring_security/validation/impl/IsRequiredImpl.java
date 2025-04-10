package com.springmc.security.spring_security.validation.impl;

import com.springmc.security.spring_security.validation.IsRequired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsRequiredImpl implements ConstraintValidator<IsRequired, String> {

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ) {
        return ( value != null && !value.trim( ).isEmpty( ) && !value.trim( ).isBlank( ) );
    }
    
}