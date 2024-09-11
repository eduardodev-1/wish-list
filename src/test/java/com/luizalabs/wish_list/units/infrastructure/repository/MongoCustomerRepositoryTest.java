package com.luizalabs.wish_list.units.infrastructure.repository;

import com.luizalabs.wish_list.domain.model.Customer;
import com.luizalabs.wish_list.domain.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
public class MongoCustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testFindById() {
        int randomInt = new Random().nextInt(101);
        Customer customer = new Customer(null, "Cliente" + randomInt, "client" + randomInt + "@gmail.com");
        Customer savedCustomer = customerRepository.save(customer);

        Optional<Customer> customerOptional = customerRepository.findById(savedCustomer.getId());
        assertThat(customerOptional.isPresent()).isTrue();
        assertThat(customerOptional.get().getId()).isEqualTo(savedCustomer.getId());
        assertThat(customerOptional.get().getName()).isEqualTo(savedCustomer.getName());
        assertThat(customerOptional.get().getEmail()).isEqualTo(savedCustomer.getEmail());
    }
}
