package com.luizalabs.wish_list.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@TypeAlias("customer")
@Document(collection = "customers")
public class Customer {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;
    private String name;
    private String email;

    public Customer() {
    }

    public Customer(String customerId) {
        this.id = customerId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
