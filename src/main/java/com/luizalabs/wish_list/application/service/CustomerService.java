package com.luizalabs.wish_list.application.service;

import com.luizalabs.wish_list.domain.model.Customer;
import com.luizalabs.wish_list.infrastructure.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
