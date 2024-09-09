package com.luizalabs.wish_list.infrastructure.repository;

import com.luizalabs.wish_list.domain.model.Product;
import com.luizalabs.wish_list.domain.repository.ProductRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoProductRepositoryImp extends ProductRepository, MongoRepository<Product, String> {
}
