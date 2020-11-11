package com.vironit.onlinepharmacy.controller;

import com.vironit.onlinepharmacy.dto.ProductCategoryDto;
import com.vironit.onlinepharmacy.model.ProductCategory;
import com.vironit.onlinepharmacy.service.product.ProductCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/productcategories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public Collection<ProductCategory> getAll() {
        return productCategoryService.getAll();
    }

    @PostMapping
    public long add(@RequestBody  @Valid ProductCategoryDto productCategoryDto) {
        return productCategoryService.add(productCategoryDto);
    }

    @GetMapping("/{id}")
    public ProductCategory get(@PathVariable Long id) {
        return productCategoryService.get(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody  @Valid ProductCategoryDto productCategoryDto, @PathVariable Long id) {
        productCategoryDto.setId(id);
        productCategoryService.update(productCategoryDto);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        productCategoryService.remove(id);
    }

}
