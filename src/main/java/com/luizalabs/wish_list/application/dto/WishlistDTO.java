package com.luizalabs.wish_list.application.dto;

import java.util.ArrayList;
import java.util.List;

public class WishlistDTO {
    private String id;

    private CustomerDTO customer;

    private List<ProductDTO> products = new ArrayList<>();

    public WishlistDTO() {
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
