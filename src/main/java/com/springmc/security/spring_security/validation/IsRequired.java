package com.springmc.security.spring_security.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.springmc.security.spring_security.validation.impl.IsRequiredImpl;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Clase anotación para la validación personalizada de un campo
 * requerido.
 * 
 * @author FernandoMendez
 * @version 1.0.0
 */
@Constraint( validatedBy = IsRequiredImpl.class )
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.FIELD, ElementType.METHOD } )
public @interface IsRequired {

	String message() default "{message.is.required}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}