package com.luizalabs.wish_list.domain.repository;

import com.luizalabs.wish_list.domain.model.Wishlist;

import java.util.Optional;

public interface WishlistRepository {
    Optional<Wishlist> findByCustomerId(String customerId);

    Wishlist save(Wishlist wishlist);
}