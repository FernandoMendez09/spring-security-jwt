package com.springmc.security.spring_security.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springmc.security.spring_security.enities.Product;
import com.springmc.security.spring_security.model.Response;
import com.springmc.security.spring_security.service.ProductService;
import com.springmc.security.spring_security.util.ResponseUtil;
import com.springmc.security.spring_security.validation.ProductValidation;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Clase controlador de nuestra app.
 * 
 * @author FernaandoMendez
 * @version 1.0.0
 */
@CrossOrigin( origins = { "http://localhost:4200" }, originPatterns = "*" )
@RestController
@PreAuthorize( "hasRole('USER')" )
@RequestMapping( "/api/product" )
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductValidation validation;

    @GetMapping( value = { "", "/" } )
    private ResponseEntity<Response> getResponseUniversal( ) {
        Response response = ResponseUtil.ok( );
        response.setData( LocalDateTime.now( ) );
        return new ResponseEntity<Response>( response, HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ADMIN')" )
    @PostMapping( "/save" )
    public ResponseEntity<Response> save( @Valid @RequestBody Product product, BindingResult result ) {
        Long id = null;

        // validation.validate( product, result );
        if( !result.hasFieldErrors( ) )
            id = productService.save( product );
        else return ResponseEntity.badRequest( ).body( ResponseUtil.invalid( result ) );

        return new ResponseEntity<Response>( ResponseUtil.ok( "Se ha insertado el producto con el id " + id ), HttpStatus.OK );
    }

    @PutMapping( "/update" )
    public ResponseEntity<Response> update( @Valid @RequestBody Product product, BindingResult result ) {
        Response response = null;

        // validation.validate( product, result );
        if( !result.hasFieldErrors( ) ) {
            Boolean isUpdated = productService.update( product );
            if( isUpdated )
                response = ResponseUtil.ok( "Se ha actualizado el producto con el id " + product.getId( ) );
            else response = ResponseUtil.error( "No se ha actualizado o no se ha podido encontrar el producto con el id " + product.getId( ) );
        } else return ResponseEntity.badRequest( ).body( ResponseUtil.invalid( result ) );

        return new ResponseEntity<Response>( response, HttpStatus.OK );
    }

    @GetMapping( "/get/{id}" )
    public ResponseEntity<Response> finById( @PathVariable Long id ) {
        Response response = ResponseUtil.ok( );
        Product product = productService.findById( id );
        if( product != null )
            response.setData( product );
        else response = ResponseUtil.error( "No se ha encontrado el producto." );
        return new ResponseEntity<Response>( response, HttpStatus.OK );
    }

    @GetMapping( "/get-all" )
    public ResponseEntity<Response> findAll( ) {
        Response response = ResponseUtil.ok( );
        List<Product> products = productService.findAll( );
        response.setData( products );
        return new ResponseEntity<Response>( response, HttpStatus.OK );
    }

    @DeleteMapping( "/remove/{id}" )
    public ResponseEntity<Response> deleteById( @PathVariable Long id ) {
        Response response = null;
        Boolean isDeleted = productService.deleteById( id );
        if( isDeleted )
            response = ResponseUtil.ok( "El producto con el id " + id + " ha sido eliminado." );
        else response = ResponseUtil.error( "El producto con el id " + id + " no ha sido eliminado o no se ha encontrado." );
        return new ResponseEntity<Response>( response, HttpStatus.OK );
    }

}