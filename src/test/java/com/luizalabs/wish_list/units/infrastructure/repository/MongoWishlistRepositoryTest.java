package com.luizalabs.wish_list.units.infrastructure.repository;

import com.luizalabs.wish_list.domain.model.Customer;
import com.luizalabs.wish_list.domain.model.Product;
import com.luizalabs.wish_list.domain.model.Wishlist;
import com.luizalabs.wish_list.domain.repository.CustomerRepository;
import com.luizalabs.wish_list.domain.repository.ProductRepository;
import com.luizalabs.wish_list.domain.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ActiveProfiles("test")
public class MongoWishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveWishlist() {
        Customer customer = new Customer(null, "Cliente1", "client1@gmail.com");
        Customer savedCustomer = customerRepository.save(customer);

        Product product = new Product(null, "produto 1", 0.79);
        Product savedProduct = productRepository.save(product);

        Wishlist wishlist = new Wishlist(savedCustomer.getId(), savedProduct.getId());
        wishlistRepository.save(wishlist);

        Optional<Wishlist> optional = wishlistRepository.findByCustomerId(savedCustomer.getId());
        assertThat(optional.isPresent()).isTrue();

        Wishlist getedWishlist = optional.get();
        assertThat(getedWishlist.getCustomer().getId()).isEqualTo(savedCustomer.getId());
        assertThat(getedWishlist.contains(product)).isTrue();
    }
}
