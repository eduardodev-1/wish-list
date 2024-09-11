package com.luizalabs.wish_list.units.infrastructure.repository;

import com.luizalabs.wish_list.domain.model.Product;
import com.luizalabs.wish_list.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ActiveProfiles("test")
public class MongoProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindById() {
        double randomDouble = new Random().nextDouble() * 101;
        int randomInt = new Random().nextInt(101);
        Product product = new Product(null, "Produto" + randomInt, randomDouble);
        Product savedProduct = productRepository.save(product);

        Optional<Product> productOptional = productRepository.findById(savedProduct.getId());
        assertThat(productOptional.isPresent()).isTrue();
        assertThat(productOptional.get().getId()).isEqualTo(savedProduct.getId());
        assertThat(productOptional.get().getName()).isEqualTo(savedProduct.getName());
        assertThat(productOptional.get().getPrice()).isEqualTo(savedProduct.getPrice());
    }
}
