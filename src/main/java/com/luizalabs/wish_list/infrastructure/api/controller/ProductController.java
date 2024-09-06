package com.luizalabs.wish_list.infrastructure.api.controller;

import com.luizalabs.wish_list.application.service.ProductService;
import com.luizalabs.wish_list.domain.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/insert")
    public ResponseEntity<Void> insertProducts(@RequestBody List<Product> products) {
        productService.insertProducts(products);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/insertOne")
    public ResponseEntity<Void> insertProducts(@RequestBody Product product) {
        productService.insertProduct(product);
        return ResponseEntity.ok().build();
    }
}
