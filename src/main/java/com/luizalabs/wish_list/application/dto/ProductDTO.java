package com.luizalabs.wish_list.application.dto;

public class ProductDTO {
    private String id;
    private String name;
    private Double price;

    public ProductDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductDTO productDTO = (ProductDTO) o;
        return id.equals(productDTO.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
