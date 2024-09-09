package com.luizalabs.wish_list.application.service;

import com.luizalabs.wish_list.application.dto.WishlistDTO;

public interface WishlistService {
    void addProduct(String customerId, String productId);

    void removeProduct(String customerId, String productId);

    WishlistDTO getWishlist(String customerId);

    boolean containsProduct(String customerId, String productId);
}
