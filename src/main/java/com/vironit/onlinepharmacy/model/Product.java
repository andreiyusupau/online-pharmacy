package com.vironit.onlinepharmacy.model;

public class Product {

    private long id;
    private String name;
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

    public long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }
}
