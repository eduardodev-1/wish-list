package com.luizalabs.wish_list.domain.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException() {
        super("Product already exists in the wishlist.");
    }
}
