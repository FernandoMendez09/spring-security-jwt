package com.springmc.security.spring_security.controller;

import org.springframework.web.bind.annotation.RestController;

import com.springmc.security.spring_security.enities.User;
import com.springmc.security.spring_security.model.Response;
import com.springmc.security.spring_security.service.UserService;
import com.springmc.security.spring_security.util.ResponseUtil;

import jakarta.validation.Valid;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@CrossOrigin( origins = { "http://localhost:4200" }, originPatterns = "*" )
@RestController
@RequestMapping( "/api" )
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping( value = { "", "/" } )
    private ResponseEntity<Response> getResponseUniversal( ) {
        Response response = ResponseUtil.ok( );
        response.setData( LocalDateTime.now( ) );
        return new ResponseEntity<Response>( response, HttpStatus.OK );
    }

    @PostMapping( "/sign-up" )
    public ResponseEntity<Response> save( @Valid @RequestBody User user, BindingResult result ) {
        if( !result.hasFieldErrors( ) )
            userService.save( user );
        else return ResponseEntity.badRequest( ).body( ResponseUtil.invalid( result ) );

        return new ResponseEntity<Response>( ResponseUtil.ok( "El usuario ha sido creado." ), HttpStatus.CREATED );
    }

}