package com.springmc.security.spring_security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springmc.security.spring_security.enities.Product;

/**
 * Interfaz para el manejo de Data JPA del objeto Producto.
 * 
 * @author FernandoMendez
 * @version 1.0.0
 */
@Repository( "productRepository" )
public interface ProductRepository extends CrudRepository<Product, Long> {

    public Boolean existsBySku(String sku);

}