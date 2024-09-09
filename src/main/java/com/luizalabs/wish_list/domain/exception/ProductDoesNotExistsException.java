package com.luizalabs.wish_list.domain.exception;

public class ProductDoesNotExistsException extends RuntimeException {
    public ProductDoesNotExistsException(String productId) {
        super("Product does not exist in the wishlist, product id: " + productId);
    }
}
