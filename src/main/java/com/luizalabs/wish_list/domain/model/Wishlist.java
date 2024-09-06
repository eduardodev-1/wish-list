package com.luizalabs.wish_list.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.List;

@TypeAlias("wishlist")
@Document(collection = "wishlist")
public class Wishlist {
    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    @DocumentReference(collection = "customers")
    private Customer customer;

    @DocumentReference(collection = "products")
    private List<Product> products = new ArrayList<>();

    public static final int MAX_PRODUCTS = 20;

    public Wishlist() {
    }

    public Wishlist(String customerId, String productId) {
        this.customer = new Customer(customerId);
        this.products = List.of(new Product(productId));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProducts(Product product) {
        checkDuplicateProduct(product);
        checkQuantityLimit();
        this.products.add(product);
    }

    private void checkQuantityLimit() {
        if (this.products.size() >= MAX_PRODUCTS) {
            throw new RuntimeException("Wishlist has reached its maximum limit of " + MAX_PRODUCTS + " products");
        }
    }

    private void checkDuplicateProduct(Product product) {
        if (this.products.contains(product)) {
            throw new RuntimeException("Product already exists in the wishlist");
        }
    }
}
