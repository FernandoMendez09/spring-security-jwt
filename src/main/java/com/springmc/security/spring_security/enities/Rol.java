package com.springmc.security.spring_security.enities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

/**
 * Entidad que representa a un rol en BD.
 * 
 * @author FernaandoMendez
 * @version 1.0.0
 */
@Entity
@Table( name = "T_ROL" )
@Data
public class Rol {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "I_ID" )
    public Long id;

    @Column( name = "V_NAME", unique = true )
    public String name;

    //Con JsonIgnoreProperties se elimina el bucle cuando la relación
    //es bidireccional
    //Sí hay un error con el proxy agregar @JsonIgnoreProperties( { "users", "handler", "hibernateLazyInitializer" } )
    @JsonIgnoreProperties( { "roles" } )
    @ManyToMany( mappedBy = "roles" )
    @ToString.Exclude
    private Set<User> users;

    public Rol( ) {
        this.users = new HashSet<>( );
    }

    public Rol( String name ) {
        this( );
        this.name = name.toUpperCase( );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Rol other = (Rol) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

}