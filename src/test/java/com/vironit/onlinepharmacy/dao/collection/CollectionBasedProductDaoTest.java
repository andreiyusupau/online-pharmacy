package com.vironit.onlinepharmacy.dao.collection;

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

    @BeforeEach
    void set() {
        product = new Product(-1, "testProduct", new BigDecimal("100"), null);
    }

    @Test
    void testAdd() {
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
    void testGet() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        long id = productDao.add(product);

        Product acquiredProduct = productDao.get(id)
                .get();

        Assertions.assertEquals(product, acquiredProduct);
    }

    @Test
    void testGetAll() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        Product secondProduct = new Product(-1, "secondTestProduct", new BigDecimal("120"), null);
        Product thirdProduct = new Product(-1, "thirdTestProduct", new BigDecimal("150"), null);

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
    void testUpdate() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        productDao.add(product);
        Product productForUpdate = new Product(0, "updatedTestProduct", new BigDecimal("180"), null);

        productDao.update(productForUpdate);

        Product updatedProduct = productDao.get(0)
                .get();

        Assertions.assertEquals(productForUpdate, updatedProduct);
    }

    @Test
    void testRemove() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        productDao.add(product);

        productDao.remove(0);
        long sizeAfterRemove = productDao.getAll()
                .size();

        Assertions.assertEquals(0, sizeAfterRemove);
    }
}
