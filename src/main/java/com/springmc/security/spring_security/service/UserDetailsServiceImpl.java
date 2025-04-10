package com.springmc.security.spring_security.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springmc.security.spring_security.enities.User;
import com.springmc.security.spring_security.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional( readOnly = true )
    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        Optional<User> oUser = userRepository.findByUsername( username );

        if( oUser.isEmpty( ) ) 
            throw new UsernameNotFoundException( String.format( "El usuario %s no existe.", username ) );
        
        User user = oUser.get( );
        List<GrantedAuthority> authorities = user.getRoles( ).stream( )
                                                .map( rol -> new SimpleGrantedAuthority( rol.getName( ) ) )
                                                .collect( Collectors.toList( ) );

        return new org.springframework.security.core.userdetails.User( user.getUsername( ),
                                                                    user.getPassword( ),
                                                                    user.getStatus( ),
                                                                    true, true, true,
                                                                    authorities );
    }

}