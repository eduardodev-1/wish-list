package com.luizalabs.wish_list.application.service;

import com.luizalabs.wish_list.application.dto.WishlistDTO;
import com.luizalabs.wish_list.domain.exception.CustomerNotFoundException;
import com.luizalabs.wish_list.domain.exception.ProductNotFoundException;
import com.luizalabs.wish_list.domain.exception.WishlistNotFoundException;
import com.luizalabs.wish_list.domain.model.Customer;
import com.luizalabs.wish_list.domain.model.Product;
import com.luizalabs.wish_list.domain.model.Wishlist;
import com.luizalabs.wish_list.domain.repository.CustomerRepository;
import com.luizalabs.wish_list.domain.repository.ProductRepository;
import com.luizalabs.wish_list.domain.repository.WishlistRepository;
import com.luizalabs.wish_list.infrastructure.api.mapper.WishlistMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WishlistServiceImp implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public WishlistServiceImp(
            WishlistRepository wishlistRepository,
            ProductRepository productRepository,
            CustomerRepository customerRepository
    ) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void addProduct(String customerId, String productId) {
        Customer customer = findCustomer(customerId);

        Product product = findProduct(productId);

        Optional<Wishlist> wishlistOptional = wishlistRepository.findByCustomerId(customerId);
        Wishlist wishlist = wishlistOptional.orElse(new Wishlist());
        if (wishlistOptional.isEmpty()) {
            createNewWishlist(wishlist, product, customer);
            return;
        }
        addProductToExistingWishlist(wishlist, product);
    }

    @Override
    public void removeProduct(String customerId, String productId) {
        findCustomer(customerId);

        Wishlist wishlist = findWishlistByCustomerId(customerId);

        wishlist.removeProduct(productId);
        wishlistRepository.save(wishlist);
    }

    @Override
    public WishlistDTO getWishlist(String customerId) {
        Wishlist wishlist = findWishlistByCustomerId(customerId);
        return WishlistMapper.INSTANCE.toDTO(wishlist);
    }

    @Override
    public boolean containsProduct(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new WishlistNotFoundException(customerId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return wishlist.contains(product);
    }

    private Customer findCustomer(String customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    private Product findProduct(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    private Wishlist findWishlistByCustomerId(String customerId) {
        return wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new WishlistNotFoundException(customerId));
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
