package com.vironit.onlinepharmacy.service.product;

import com.vironit.onlinepharmacy.dto.ProductCreateData;
import com.vironit.onlinepharmacy.dto.ProductUpdateData;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.service.CrudService;

public interface ProductService extends CrudService<ProductCreateData, Product, ProductUpdateData> {
}
