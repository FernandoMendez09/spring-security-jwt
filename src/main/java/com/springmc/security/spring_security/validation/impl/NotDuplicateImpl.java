package com.springmc.security.spring_security.validation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springmc.security.spring_security.service.ProductService;
import com.springmc.security.spring_security.validation.NotDuplicate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class NotDuplicateImpl implements ConstraintValidator<NotDuplicate, String> {

    @Autowired
    private ProductService productService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if( this.productService == null )
        return true;
        return !productService.existsBySku( value );
    }

}