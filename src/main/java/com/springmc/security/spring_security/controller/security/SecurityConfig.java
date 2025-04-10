package com.springmc.security.spring_security.controller.security;

import static com.springmc.security.spring_security.controller.security.TokenJwtConfig.CONTENT_TYPE;
import static com.springmc.security.spring_security.controller.security.TokenJwtConfig.HEADER_AUTHORIZATION;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.springmc.security.spring_security.controller.security.filter.JwtAuthenticationFilter;
import com.springmc.security.spring_security.controller.security.filter.JwtValidationFilter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager( ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager( );
    }

    @Bean
    PasswordEncoder passwordEncoder( ) {
        return new BCryptPasswordEncoder( );
    }

    @Bean
    SecurityFilterChain filterChain( HttpSecurity http ) throws Exception {
        return http.authorizeHttpRequests( auth -> {
                auth.requestMatchers( HttpMethod.GET, "/api/" ).permitAll( )
                .requestMatchers( HttpMethod.POST, "/api/sign-up" ).permitAll( )
                // .requestMatchers( HttpMethod.POST, "/api/product/save" ).hasRole( "ADMIN" )
                .anyRequest( ).authenticated( );
            } )
            .addFilter( new JwtAuthenticationFilter( authenticationManager( ) ) )
            .addFilter( new JwtValidationFilter( authenticationManager( ) ) )
            .csrf( config -> config.disable( ) )
            .cors( cors -> cors.configurationSource( corsConfigurationSource( ) ) )
            .sessionManagement( session -> session.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) )
            .build( );
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource( ) {
        CorsConfiguration config = new CorsConfiguration( );
        config.setAllowedOrigins( Arrays.asList( "http://localhost:4200" ) );
        config.setAllowedOriginPatterns( Arrays.asList( "*" ) );
        config.setAllowedMethods( Arrays.asList( HttpMethod.GET.name( ),
                                                 HttpMethod.POST.name( ),
                                                 HttpMethod.PUT.name( ),
                                                 HttpMethod.DELETE.name( ) ) );
        config.setAllowedHeaders( Arrays.asList( HEADER_AUTHORIZATION, CONTENT_TYPE ) );
        config.setAllowCredentials( true );

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource( );
        source.registerCorsConfiguration( "/**", config );
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter( ) {
        FilterRegistrationBean<CorsFilter> corsBean =
            new FilterRegistrationBean<>( new CorsFilter( corsConfigurationSource( ) ) );
        corsBean.setOrder( Ordered.HIGHEST_PRECEDENCE );
        return corsBean;
    }

}