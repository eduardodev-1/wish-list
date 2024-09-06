package com.luizalabs.wish_list.application.service;

import com.luizalabs.wish_list.domain.model.Product;
import com.luizalabs.wish_list.infrastructure.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void insertProducts(List<Product> products) {
        productRepository.saveAll(products);
    }

    public void insertProduct(Product product) {
        productRepository.save(product);
    }
}
