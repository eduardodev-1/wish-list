package com.luizalabs.wish_list.infrastructure.repository;

import com.luizalabs.wish_list.domain.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
}