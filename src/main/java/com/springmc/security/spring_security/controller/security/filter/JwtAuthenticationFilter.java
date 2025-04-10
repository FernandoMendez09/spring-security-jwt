package com.springmc.security.spring_security.controller.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmc.security.spring_security.enities.User;
import static com.springmc.security.spring_security.controller.security.TokenJwtConfig.SECRET_KEY;
import static com.springmc.security.spring_security.controller.security.TokenJwtConfig.PREFIX_TOKEN;
import static com.springmc.security.spring_security.controller.security.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.springmc.security.spring_security.controller.security.TokenJwtConfig.JSON;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter( AuthenticationManager authenticationManager ) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response )
            throws AuthenticationException {
        User user = null;
        String username = null;
        String password = null;

        try {
            user = new ObjectMapper( ).readValue( request.getInputStream( ), User.class );
            username = user.getUsername( );
            password = user.getPassword( );
        } catch( StreamReadException e ) {
            e.printStackTrace( );
        } catch( DatabindException e ) {
            e.printStackTrace( );
        } catch( IOException e ) {
            e.printStackTrace( );
        }

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken( username, password );

        return authenticationManager.authenticate( authenticationToken );
    }

    @Override
    protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult ) throws IOException, ServletException {

        String username = ( ( org.springframework.security.core.userdetails.User )
                        authResult.getPrincipal( ) ).getUsername( );

        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities( );

        Claims claims = Jwts.claims( )
            .add( "authorities", new ObjectMapper( ).writeValueAsString( roles ) )
            .build( );

        String token = Jwts.builder( )
                        .subject( username )
                        .claims( claims )
                        .expiration( new Date( System.currentTimeMillis( ) + 3600000 ) ) //Tiempo en ms - 1hr 3.6M ms
                        .issuedAt( new Date( ) )
                        .signWith( SECRET_KEY )
                        .compact( );

        response.addHeader( HEADER_AUTHORIZATION, PREFIX_TOKEN + token );
        Map<String, String> body = new HashMap<>( );
        body.put( "token", token );
        body.put( "user", username );
        response.getWriter( ).write( new ObjectMapper( ).writeValueAsString( body ) );
        response.setContentType( JSON );
        response.setStatus( HttpStatus.OK.value( ) );
    }

    @Override
    protected void unsuccessfulAuthentication( HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed ) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>( );
        body.put( "message", "Usuario o contraseña incorrectos." );
        body.put( "error", failed.getMessage( ) );
        response.getWriter( ).write( new ObjectMapper( ).writeValueAsString( body ) );
        response.setContentType( JSON );
        response.setStatus( HttpStatus.UNAUTHORIZED.value( ) );
    }

    
}