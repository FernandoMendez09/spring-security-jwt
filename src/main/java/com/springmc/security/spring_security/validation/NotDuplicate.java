package com.springmc.security.spring_security.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.springmc.security.spring_security.validation.impl.NotDuplicateImpl;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Clase anotación para la validación personalizada de un campo
 * no duplicado en bd.
 * 
 * @author FernandoMendez
 * @version 1.0.0
 */
@Constraint( validatedBy = NotDuplicateImpl.class )
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface NotDuplicate {

	String message() default "{message.not.duplicate}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}