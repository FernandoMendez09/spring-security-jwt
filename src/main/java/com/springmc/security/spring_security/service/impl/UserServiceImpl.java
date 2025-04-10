package com.springmc.security.spring_security.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springmc.security.spring_security.enities.Rol;
import com.springmc.security.spring_security.enities.User;
import com.springmc.security.spring_security.repositories.RolRepository;
import com.springmc.security.spring_security.repositories.UserRepository;
import com.springmc.security.spring_security.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional( readOnly = true )
    public User login( User user ) {

        Optional<User> oUser = userRepository.findByUsername( user.getUsername( ) );
        if( oUser.isPresent( ) ) {
            User returnedUser = oUser.get( );
            if( encoder.matches( user.getPassword( ), returnedUser.getPassword( ) ) )
            return returnedUser;
        }

        return null;

    }

    @Override
    @Transactional
    public Long save( User user ) {

        Set<Rol> roles = new HashSet<>( );
        rolRepository.findByName( "ROLE_USER" ).ifPresent( roles::add );

        if( user.isAdmin( ) )
            rolRepository.findByName( "ROLE_ADMIN" ).ifPresent( roles::add );

        user.setRoles( roles );
        user.setPassword( encoder.encode( user.getPassword( ) ) );
        user.setStatus( true );
        User savedUser = userRepository.save( user );
        return savedUser.getId( );

    }

    @Override
    @Transactional
    public Boolean update( User user ) {

        Optional<User> oUser = userRepository.findById( user.getId( ) );
        if( oUser.isPresent( ) ) {
            User pUser = oUser.get( );
            pUser.setPassword( user.getPassword( ) );
            pUser.setStatus( user.getStatus( ) );
            pUser.setUsername( user.getUsername( ) );
            pUser.setRoles( user.getRoles( ) );
            userRepository.save( pUser );
        }
        return false;

    }

}