package com.springmc.security.spring_security.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springmc.security.spring_security.enities.Product;
import com.springmc.security.spring_security.repositories.ProductRepository;
import com.springmc.security.spring_security.service.ProductService;

@Service( "productService" )
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Transactional
    @Override
    public Long save( Product product ) {
        Product savedProduct = productRepository.save( product );
        return savedProduct.getId( );
    }

    @Transactional
    @Override
    public Boolean update( Product product ) {
        Optional<Product> oProduct = productRepository.findById( product.getId( ) );
        if( oProduct.isPresent( ) ) {
            Product productDB = oProduct.get( );
            productDB.setName( product.getName( ) );
            productDB.setPrice( product.getPrice( ) );
            productDB.setSku( product.getSku( ) );
            productDB.setDescription( product.getDescription( ) );
            productRepository.save( productDB );
            return true;
        } else return false;
    }

    @Transactional( readOnly = true )
    @Override
    public Product findById( Long id ) {
        Optional<Product> oProduct = productRepository.findById( id );
        if( oProduct.isPresent( ) )
            return oProduct.get( );
        return null;
    }

    @Transactional( readOnly = true )
    @Override
    public List<Product> findAll( ) {
        return ( List<Product> ) productRepository.findAll( );
    }

    @Transactional
    @Override
    public Boolean deleteById(Long id) {
        if( !productRepository.existsById( id ) )
            return false;
        productRepository.deleteById( id );
        return !productRepository.existsById( id );
    }

    @Transactional( readOnly = true )
    @Override
    public Boolean existsBySku( String sku ) {
        return productRepository.existsBySku( sku );
    }

}