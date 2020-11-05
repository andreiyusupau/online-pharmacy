package com.vironit.onlinepharmacy.controller;

import com.vironit.onlinepharmacy.dto.ProductDto;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.service.product.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Collection<Product> getAll() {
        return productService.getAll();
    }

    @PostMapping
    public long add(@RequestBody ProductDto productDto) {
        return productService.add(productDto);
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return productService.get(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody ProductDto productDto, @PathVariable Long id) {
        productDto.setId(id);
        productService.update(productDto);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        productService.remove(id);
    }

}
