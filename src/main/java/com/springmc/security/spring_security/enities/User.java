package com.springmc.security.spring_security.enities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springmc.security.spring_security.validation.IsRequired;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

/**
 * Entidad que representa a un usuario en BD.
 * 
 * @author FernaandoMendez
 * @version 1.0.0
 */
@Entity
@Table( name = "T_USER" )
@Data
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "I_ID" )
    private Long id;

    @IsRequired
    @Column( name = "V_USERNAME", unique = true )
    private String username;

    @IsRequired
    @Column( name = "V_PASSWORD" )
    @JsonProperty( access = JsonProperty.Access.WRITE_ONLY )
    private String password;

    @Column( name = "I_STATUS" )
    @JsonProperty( access = JsonProperty.Access.WRITE_ONLY )
    private Boolean status;

    //Con JsonIgnoreProperties se elimina el bucle cuando la relación
    //es bidireccional.
    //Sí hay un error con el proxy agregar @JsonIgnoreProperties( { "users", "handler", "hibernateLazyInitializer" } )
    @JsonIgnoreProperties( { "users" } )
    @ManyToMany
    @JoinTable( name = "T_USER_ROL",
                joinColumns = @JoinColumn( name = "I_ID_USER" ),
                inverseJoinColumns = @JoinColumn( name = "I_ID_ROL" ),
                uniqueConstraints = @UniqueConstraint( columnNames = { "I_ID_USER", "I_ID_ROL" } ) )
    private Set<Rol> roles;

    //Anotación para un campo intrascendente para la bd.
    @Transient
    @JsonProperty( access = JsonProperty.Access.WRITE_ONLY )
    private boolean admin;

    public User( ) {
        this.roles = new HashSet<>( );
        this.status = true;
    }

    public User( String username, String password, Boolean status ) {
        this( );
        this.username = username.toLowerCase( );
        this.password = password;
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

}