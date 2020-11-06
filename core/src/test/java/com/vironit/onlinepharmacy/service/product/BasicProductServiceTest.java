package com.vironit.onlinepharmacy.service.product;

import com.vironit.onlinepharmacy.dao.ProductDao;
import com.vironit.onlinepharmacy.dto.ProductDto;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.service.exception.ProductServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
@Disabled
@ExtendWith(MockitoExtension.class)
public class BasicProductServiceTest {

    @Mock
    private ProductDao productDao;
    @InjectMocks
    private BasicProductService productService;

    private ProductDto productDto;
    private Product product;
    private Product secondProduct;
    private Collection<Product> products;

    @BeforeEach
    void set() {
        productDto =new ProductDto("testProduct", new BigDecimal(1245), 0, false);
        product = new Product(1, "testProduct", new BigDecimal(1245), null, false);
        secondProduct = new Product(2, "secondTestProduct", new BigDecimal(1632), null, false);
        products = new ArrayList<>();
        products.add(product);
        products.add(secondProduct);
    }

    @Test
    void addShouldUseDao() {
        when(productDao.add(any()))
                .thenReturn(0L);

        long id = productService.add(productDto);

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

        Exception exception = Assertions.assertThrows(ProductServiceException.class, () -> productService.get(1));

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
        when(productDao.update(any()))
                .thenReturn(true);
ProductDto productDtoForUpdate =new ProductDto( "updatedName", new BigDecimal("1245"), 0, false);
productDtoForUpdate.setId(1);
productService.update(productDtoForUpdate);

        Product productForUpdate = new Product(1, "updatedName", new BigDecimal("1245"), null, false);
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
