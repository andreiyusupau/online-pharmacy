package com.vironit.onlinepharmacy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private BigDecimal price;
    private ProductCategory productCategory;
    private boolean recipeRequired;

    public Product() {
    }

    public Product(long id, String name, BigDecimal price, ProductCategory productCategory, boolean recipeRequired) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productCategory = productCategory;
        this.recipeRequired = recipeRequired;
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

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public boolean isRecipeRequired() {
        return recipeRequired;
    }

    public void setRecipeRequired(boolean recipeRequired) {
        this.recipeRequired = recipeRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                name.equals(product.name) &&
                price.equals(product.price) &&
                productCategory.equals(product.productCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, productCategory);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", productCategory=" + productCategory +
                '}';
    }
}
