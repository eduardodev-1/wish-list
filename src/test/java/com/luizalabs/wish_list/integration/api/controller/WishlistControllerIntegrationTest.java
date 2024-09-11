package com.luizalabs.wish_list.integration.api.controller;

import com.luizalabs.wish_list.application.service.WishlistService;
import com.luizalabs.wish_list.domain.model.Customer;
import com.luizalabs.wish_list.domain.model.Product;
import com.luizalabs.wish_list.domain.repository.CustomerRepository;
import com.luizalabs.wish_list.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WishlistControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        int randomInt = new Random().nextInt(101);
        customer = customerRepository.save(new Customer(null, "Cliente Teste " + randomInt, "cliente.teste" + randomInt + "@gmail.com"));
    }

    @Test
    void testAddProductSuccess() throws Exception {
        int randomInt = new Random().nextInt(101);
        double randomDouble = new Random().nextDouble(101);
        Product product = productRepository.save(new Product(null, "Produto Teste " + randomInt, randomDouble));
        mockMvc.perform(post("/api/wishlist/{customerId}/add", customer.getId())
                        .param("productId", product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Produto adicionado com sucesso!"));
    }

    @Test
    void testRemoveProductSuccess() throws Exception {
        Product productToRemove = productRepository.save(new Product(null, "Produto Teste", 50.0));
        wishlistService.addProduct(customer.getId(), productToRemove.getId());

        mockMvc.perform(delete("/api/wishlist/{customerId}/remove", customer.getId())
                        .param("productId", productToRemove.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Produto removido com sucesso!"));
    }

    @Test
    void testGetWishlistSuccess() throws Exception {
        int randomInt = new Random().nextInt(101);
        double randomDouble = new Random().nextDouble(101);
        Product product = productRepository.save(new Product(null, "Produto Teste " + randomInt, randomDouble));
        wishlistService.addProduct(customer.getId(), product.getId());

        mockMvc.perform(get("/api/wishlist/{customerId}", customer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testContainsProductSuccess() throws Exception {
        int randomInt = new Random().nextInt(101);
        double randomDouble = new Random().nextDouble(101);
        Product product = productRepository.save(new Product(null, "Produto Teste " + randomInt, randomDouble));
        wishlistService.addProduct(customer.getId(), product.getId());

        mockMvc.perform(get("/api/wishlist/{customerId}/contains", customer.getId())
                        .param("productId", product.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
