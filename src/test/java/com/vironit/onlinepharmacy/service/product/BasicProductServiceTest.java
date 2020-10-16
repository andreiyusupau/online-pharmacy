package com.vironit.onlinepharmacy.service.product;

import com.vironit.onlinepharmacy.dao.ProductDao;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.service.product.exception.ProductException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BasicProductServiceTest {

    @Mock
    private ProductDao productDao;
    @InjectMocks
    private BasicProductService productService;

    private Product product;
    private Product secondProduct;
    private Collection<Product> products;

    @BeforeEach
    void set() {
        product = new Product(1, "testProduct", new BigDecimal(1245), null,false);
        product = new Product(2, "secondTestProduct", new BigDecimal(1632), null,false);
        products = new ArrayList<>();
        products.add(product);
        products.add(secondProduct);
    }

    @Test
    void addShouldUseDao() {
        when(productDao.add(any()))
                .thenReturn(0L);

        long id = productService.add(product);

        verify(productDao).add(product);
        Assertions.assertEquals(0, id);
    }

    @Test
    void getShouldNotThrowException() {
        when(productDao.get(anyLong()))
                .thenReturn(Optional.of(product));

        Product actualProduct = productService.get(1);

        verify(productDao).get(1);
        Assertions.assertEquals(product, actualProduct);
    }

    @Test
    void getShouldThrowException() {
        when(productDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ProductException.class, () -> productService.get(1));

        verify(productDao).get(1);
        String expectedMessage = "Can't get product. Product with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getAllShouldNotThrowException() {
        when(productDao.getAll())
                .thenReturn(products);

        Collection<Product> actualProducts = productService.getAll();

        Assertions.assertEquals(products, actualProducts);
    }

    @Test
    void updateShouldUseDao() {
        Product productForUpdate = new Product(1, "updatedName", new BigDecimal("1245"), null,false);
        when(productDao.update(any()))
                .thenReturn(true);

        productService.update(productForUpdate);

        verify(productDao).update(productForUpdate);
    }

    @Test
    void removeShouldUseDao() {
        when(productDao.remove(anyLong()))
                .thenReturn(true);

        productService.remove(1);

        verify(productDao).remove(1);
    }
}
