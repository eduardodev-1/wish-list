package com.luizalabs.wish_list.application.dto;

import com.luizalabs.wish_list.domain.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class WishlistDTO {
    private String id;

    private CustomerDTO customer;

    private List<ProductDTO> products = new ArrayList<>();

    public WishlistDTO() {
    }

    public WishlistDTO(String customerId, String productId) {
        this.customer = new CustomerDTO(customerId);
        this.products = List.of(new ProductDTO(productId));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
