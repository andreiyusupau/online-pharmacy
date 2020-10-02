package com.vironit.onlinepharmacy.model;

import java.math.BigDecimal;

public class Product {

    private long id;
    private String name;
    private BigDecimal price;
    private long productCategoryId;

    public Product() {
    }

    public Product(long id, String name, long productCategoryId) {
        this.id = id;
        this.name = name;
       this.productCategoryId=productCategoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }
}
