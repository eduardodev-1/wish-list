package com.luizalabs.wish_list.application.service;

import com.luizalabs.wish_list.domain.model.Customer;
import com.luizalabs.wish_list.domain.model.Product;
import com.luizalabs.wish_list.domain.model.Wishlist;
import com.luizalabs.wish_list.infrastructure.repository.CustomerRepository;
import com.luizalabs.wish_list.infrastructure.repository.ProductRepository;
import com.luizalabs.wish_list.infrastructure.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    private final ProductRepository productRepository;

    private final CustomerRepository customerRepository;

    public WishlistService(
            WishlistRepository wishlistRepository,
            ProductRepository productRepository,
            CustomerRepository customerRepository
    ) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public void addProduct(String customerId, String productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + customerId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        Optional<Wishlist> wishlistOptional = wishlistRepository.findByCustomerId(customerId);
        Wishlist wishlist = wishlistOptional.orElse(new Wishlist());
        if (wishlistOptional.isEmpty()) {
            createNewWishlist(wishlist, product, customer);
            return;
        }
        addProductToExistingWishlist(wishlist, product);
    }

    public void removeProduct(String customerId, String productId) {
    }

    public List<Product> getWishlist(String customerId) {
        return List.of();
    }

    public boolean containsProduct(String customerId, String productId) {
        return false;
    }

    private void addProductToExistingWishlist(Wishlist wishlist, Product product) {
        wishlist.addProducts(product);
        wishlistRepository.save(wishlist);
    }

    private void createNewWishlist(Wishlist wishlist, Product product, Customer customer) {
        wishlist.addProducts(product);
        wishlist.setCustomer(customer);
        wishlistRepository.save(wishlist);
    }
}
