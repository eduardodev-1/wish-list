package com.luizalabs.wish_list.domain.repository;

import com.luizalabs.wish_list.domain.model.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(String customerId);
}