package com.luizalabs.wish_list.domain.repository;

import com.luizalabs.wish_list.domain.model.Customer;
import com.luizalabs.wish_list.domain.model.Wishlist;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(String customerId);
    Customer save(Customer customer);
}