package com.luizalabs.wish_list.application.service;

import com.luizalabs.wish_list.application.dto.WishlistDTO;
import com.luizalabs.wish_list.domain.exception.CustomerNotFoundException;
import com.luizalabs.wish_list.domain.exception.ProductNotFoundException;
import com.luizalabs.wish_list.domain.exception.WishlistNotFoundException;
import com.luizalabs.wish_list.domain.model.Customer;
import com.luizalabs.wish_list.domain.model.Product;
import com.luizalabs.wish_list.domain.model.Wishlist;
import com.luizalabs.wish_list.infrastructure.api.mapper.WishlistMapper;
import com.luizalabs.wish_list.infrastructure.repository.CustomerRepository;
import com.luizalabs.wish_list.infrastructure.repository.ProductRepository;
import com.luizalabs.wish_list.infrastructure.repository.WishlistRepository;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Optional<Wishlist> wishlistOptional = wishlistRepository.findByCustomerId(customerId);
        Wishlist wishlist = wishlistOptional.orElse(new Wishlist());
        if (wishlistOptional.isEmpty()) {
            createNewWishlist(wishlist, product, customer);
            return;
        }
        addProductToExistingWishlist(wishlist, product);
    }

    public void removeProduct(String customerId, String productId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new WishlistNotFoundException(customerId));

        wishlist.removeProduct(product);
        wishlistRepository.save(wishlist);
    }

    public WishlistDTO getWishlist(String customerId) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new WishlistNotFoundException(customerId));

        return WishlistMapper.INSTANCE.toDTO(wishlist);
    }

    public boolean containsProduct(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new WishlistNotFoundException(customerId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return wishlist.contains(product);
    }

    private void addProductToExistingWishlist(Wishlist wishlist, Product product) {
        wishlist.addProduct(product);
        wishlistRepository.save(wishlist);
    }

    private void createNewWishlist(Wishlist wishlist, Product product, Customer customer) {
        wishlist.addProduct(product);
        wishlist.setCustomer(customer);
        wishlistRepository.save(wishlist);
    }
}
