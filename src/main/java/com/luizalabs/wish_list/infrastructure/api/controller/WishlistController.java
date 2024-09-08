package com.luizalabs.wish_list.infrastructure.api.controller;

import com.luizalabs.wish_list.application.dto.WishlistDTO;
import com.luizalabs.wish_list.application.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/{customerId}/add")
    public ResponseEntity<String> addProduct(@PathVariable String customerId, @RequestParam String productId) {
        wishlistService.addProduct(customerId, productId);
        return ResponseEntity.ok("Produto adicionado com sucesso!");
    }

    @DeleteMapping("/{customerId}/remove")
    public ResponseEntity<String> removeProduct(@PathVariable String customerId, @RequestParam String productId) {
        wishlistService.removeProduct(customerId, productId);
        return ResponseEntity.ok("Produto removido com sucesso!");
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<WishlistDTO> getWishlist(@PathVariable String customerId) {
        WishlistDTO wishlist = wishlistService.getWishlist(customerId);
        return ResponseEntity.ok(wishlist);
    }

    @GetMapping("/{customerId}/contains")
    public ResponseEntity<Boolean> containsProduct(@PathVariable String customerId, @RequestParam String productId) {
        boolean exists = wishlistService.containsProduct(customerId, productId);
        return ResponseEntity.ok(exists);
    }
}
