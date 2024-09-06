package com.luizalabs.wish_list.infrastructure.repository;

import com.luizalabs.wish_list.domain.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
