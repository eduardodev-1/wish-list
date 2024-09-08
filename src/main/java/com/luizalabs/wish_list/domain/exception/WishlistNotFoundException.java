package com.luizalabs.wish_list.domain.exception;

public class WishlistNotFoundException extends RuntimeException {
    public WishlistNotFoundException(String customerId) {
        super("Wishlist not found for customer: " + customerId);
    }
}