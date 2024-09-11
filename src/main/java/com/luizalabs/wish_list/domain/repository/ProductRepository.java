package com.luizalabs.wish_list.domain.repository;

import com.luizalabs.wish_list.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(String productId);
    Product save(Product product);
}