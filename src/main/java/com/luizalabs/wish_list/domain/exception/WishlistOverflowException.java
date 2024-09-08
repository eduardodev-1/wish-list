package com.luizalabs.wish_list.domain.exception;

import com.luizalabs.wish_list.domain.model.Wishlist;

public class WishlistOverflowException extends RuntimeException {
    public WishlistOverflowException() {
        super("Wishlist has reached its maximum limit of " + Wishlist.MAX_PRODUCTS + " products.");
    }
}
