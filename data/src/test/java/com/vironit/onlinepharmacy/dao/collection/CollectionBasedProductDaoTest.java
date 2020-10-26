package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.collection.util.IdGenerator;
import com.vironit.onlinepharmacy.model.Product;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollectionBasedProductDaoTest {

    @Mock
    private IdGenerator idGenerator;
    @InjectMocks
    private CollectionBasedProductDao productDao;

    private Product product;
    private Product secondProduct;
    private Product thirdProduct;

    @BeforeEach
    void set() {
        product = new Product(-1, "testProduct", new BigDecimal("100"), null, false);
        secondProduct = new Product(-1, "secondTestProduct", new BigDecimal("120"), null, false);
        thirdProduct = new Product(-1, "thirdTestProduct", new BigDecimal("150"), null, false);
    }

    @Test
    void addShouldAddProductToCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);

        long id = productDao.add(product);

        long sizeAfterAdd = productDao.getAll()
                .size();
        Assertions.assertEquals(1, sizeAfterAdd);
        verify(idGenerator, times(1)).getNextId();
        Assertions.assertEquals(0, id);
    }

    @Test
    void getShouldGetProductFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        long id = productDao.add(product);

        Product acquiredProduct = productDao.get(id)
                .get();

        Assertions.assertEquals(product, acquiredProduct);
    }

    @Test
    void getAllShouldGetAllProductsFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        productDao.add(product);
        productDao.add(secondProduct);
        productDao.add(thirdProduct);

        Collection<Product> acquiredProducts = productDao.getAll();

        Collection<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(product);
        expectedProducts.add(secondProduct);
        expectedProducts.add(thirdProduct);
        Assertions.assertEquals(expectedProducts, acquiredProducts);
    }

    @Test
    void updateShouldUpdateProductInCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        productDao.add(product);
        Product productForUpdate = new Product(0, "updatedTestProduct", new BigDecimal("180"), null, false);

        productDao.update(productForUpdate);

        Product updatedProduct = productDao.get(0)
                .get();

        Assertions.assertEquals(productForUpdate, updatedProduct);
    }

    @Test
    void removeShouldRemoveProductFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        productDao.add(product);

        productDao.remove(0);
        long sizeAfterRemove = productDao.getAll()
                .size();

        Assertions.assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualZero() {
        int totalElements = productDao.getTotalElements();
        Assertions.assertEquals(0, totalElements);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualTwo() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L);
        productDao.add(product);
        productDao.add(secondProduct);
        int totalElements = productDao.getTotalElements();
        Assertions.assertEquals(2, totalElements);
    }

    @Test
    void getPageShouldReturnASecondPageWithOneElement() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        productDao.add(product);
        productDao.add(secondProduct);
        productDao.add(thirdProduct);

        Collection<Product> productPage = productDao.getPage(2, 2);

        Collection<Product> expectedProductPage = new ArrayList<>();
        expectedProductPage.add(thirdProduct);
        Assertions.assertEquals(expectedProductPage, productPage);
    }

    @Test
    void getPageShouldReturnAThirdPageWithNoElements() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        productDao.add(product);
        productDao.add(secondProduct);
        productDao.add(thirdProduct);

        Collection<Product> productPage = productDao.getPage(3, 2);

        Collection<Product> expectedProductPage = new ArrayList<>();
        Assertions.assertEquals(expectedProductPage, productPage);
    }
}
