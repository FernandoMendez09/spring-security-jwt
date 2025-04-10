package com.springmc.security.spring_security.service;

import java.util.List;

import com.springmc.security.spring_security.enities.Product;

/**
 * Clase service para el manejo correcto entre el controlador y el repositorio
 * para el objeto Producto.
 * 
 * @author FernandoMendez
 * @version 1.0.0
 */
public interface ProductService {

    /**
     * Método para llamar al repositorio e insertar un producto.
     * 
     * @param product {@link Product} Objeto a insertar.
     * @return id {@link Long} Identificador del producto.
     */
    public Long save( Product product );

    /**
     * Método para llamar al repositorio y actualizar un producto.
     * 
     * @param product {@link Product} Objeto a actualizar.
     * @return isUpdated {@link Boolean} Sí el producto fue actualizado.
     */
    public Boolean update( Product product );

    /**
     * Método para llamar al repositorio y consultar un producto.
     * 
     * @param id {@link Long} Identificador a buscar.
     * @return product {@link Product} Producto consultado.
     */
    public Product findById( Long id );

    /**
     * Método para llamar al repositorio y consultar todos los productos.
     * 
     * @return products {@link List} Todos los productos.
     */
    public List<Product> findAll( );

    /**
     * Método para llamar al repositorio y eliminar un producto.
     * 
     * @param id {@link Long} Identificador a buscar.
     * @return isDeleted {@link Boolean} Producto eliminado.
     */
    public Boolean deleteById( Long id );

    /**
     * Método para llamar al repositorio y verificar si existe un
     * código sku.
     * 
     * @param sku {@link String} Código SKU a consultar.
     * @return exists {@link Boolean} Sí existe.
     */
    public Boolean existsBySku(String sku);

}