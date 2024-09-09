package com.luizalabs.wish_list.infrastructure.repository;

import com.luizalabs.wish_list.domain.model.Wishlist;
import com.luizalabs.wish_list.domain.repository.WishlistRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoWishlistRepositoryImp extends WishlistRepository, MongoRepository<Wishlist, String> {
    Optional<Wishlist> findByCustomerId(String customerId);
}