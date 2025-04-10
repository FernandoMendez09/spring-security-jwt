package com.springmc.security.spring_security.enities;

import com.springmc.security.spring_security.validation.IsRequired;
import com.springmc.security.spring_security.validation.NotDuplicate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Entidad que representa a un producto en BD.
 * 
 * @author FernaandoMendez
 * @version 1.0.0
 */
@Entity
@Table( name = "T_PRODUCT" )
@Data
public class Product {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "I_ID" )
    private Long id;

    // @NotBlank( message = "{message.not.blank}" )
    // @NotNull( message = "{message.not.null}" )
    @IsRequired
    @Size( min = 3, max = 45, message = "{message.size}" )
    @Column( name = "V_NAME" )
    private String name;

    @NotNull( message = "{message.not.null}" )
    @Min( value = 1, message = "{message.min}" )
    @Max( value = 100000, message = "{message.max}" )
    @Column( name = "I_PRICE" )
    private Float price;

    @IsRequired
    @NotDuplicate
    @Size( min = 5, max = 15, message = "{message.size}" )
    @Column( name = "V_SKU_CODE" )
    private String sku;

    @NotBlank( message = "{message.not.blank}" )
    @Column( name = "V_DESCRIPTION" )
    private String description;

    public Product( ) { }

    public Product( String name, Float price, String sku ) {
        this.name = name.toUpperCase( );
        this.price = price;
        this.sku = sku.toUpperCase( );
    }

    public Product( String name, Float price, String sku, String description ) {
        this.name = name.toUpperCase( );
        this.price = price;
        this.sku = sku.toUpperCase( );
        this.description = description.toUpperCase( );
    }

}