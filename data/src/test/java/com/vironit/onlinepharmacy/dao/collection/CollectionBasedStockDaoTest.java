package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.collection.util.IdGenerator;
import com.vironit.onlinepharmacy.model.Position;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.StockPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CollectionBasedStockDaoTest {

    @Mock
    private IdGenerator idGenerator;
    @InjectMocks
    private CollectionBasedStockDao stockDao;

    private Product product;
    private Product secondProduct;
    private Product thirdProduct;
    private StockPosition position;
    private StockPosition secondPosition;
    private StockPosition thirdPosition;

    @BeforeEach
    void set() {
        product = new Product(1, "testProduct", new BigDecimal("100"), null, false);
        secondProduct = new Product(2, "secondTestProduct", new BigDecimal("120"), null, false);
        thirdProduct = new Product(3, "thirdTestProduct", new BigDecimal("180"), null, false);
        position = new StockPosition(1, 10, product, 0);
        secondPosition = new StockPosition(2, 11, secondProduct, 0);
        thirdPosition = new StockPosition(3, 14, thirdProduct, 0);
    }

    @Test
    void getByProductIdShouldReturnPositionWithProductId() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        stockDao.add(position);

        Position acquiredPosition = stockDao.getByProductId(1)
                .get();

        assertEquals(position, acquiredPosition);
    }

    @Test
    void addShouldAddPositionToCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        long id = stockDao.add(position);

        long sizeAfterAdd = stockDao.getAll()
                .size();
        assertEquals(1, sizeAfterAdd);
        assertEquals(0, id);
    }

    @Test
    void getShouldGetPositionFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        long id = stockDao.add(position);

        Position acquiredPosition = stockDao.get(id)
                .get();

        assertEquals(position, acquiredPosition);
    }

    @Test
    void getAllShouldGetAllPositionsFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        stockDao.add(position);
        stockDao.add(secondPosition);
        stockDao.add(thirdPosition);

        Collection<StockPosition> acquiredPositions = stockDao.getAll();

        Collection<StockPosition> expectedPositions = new ArrayList<>();
        expectedPositions.add(position);
        expectedPositions.add(secondPosition);
        expectedPositions.add(thirdPosition);
        assertEquals(expectedPositions, acquiredPositions);
    }

    @Test
    void updateShouldUpdatePositionInCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        stockDao.add(position);
        StockPosition positionForUpdate = new StockPosition(0, 15, product, 0);

        stockDao.update(positionForUpdate);

        StockPosition updatedPosition = stockDao.get(0)
                .get();

        assertEquals(positionForUpdate, updatedPosition);
    }

    @Test
    void removeShouldRemovePositionFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        stockDao.add(position);

        stockDao.remove(0);

        long sizeAfterRemove = stockDao.getAll()
                .size();
        assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualZero() {
        long totalElements = stockDao.getTotalElements();
        assertEquals(0, totalElements);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualTwo() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L);
        stockDao.add(position);
        stockDao.add(secondPosition);
        long totalElements = stockDao.getTotalElements();
        assertEquals(2, totalElements);
    }

    @Test
    void getPageShouldReturnASecondPageWithOneElement() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        stockDao.add(position);
        stockDao.add(secondPosition);
        stockDao.add(thirdPosition);

        Collection<StockPosition> positionPage = stockDao.getPage(2, 2);

        Collection<StockPosition> expectedPositionPage = new ArrayList<>();
        expectedPositionPage.add(thirdPosition);
        assertEquals(expectedPositionPage, positionPage);
    }

    @Test
    void getPageShouldReturnAThirdPageWithNoElements() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        stockDao.add(position);
        stockDao.add(secondPosition);
        stockDao.add(thirdPosition);

        Collection<StockPosition> positionPage = stockDao.getPage(3, 2);

        Collection<StockPosition> expectedPositionPage = new ArrayList<>();
        assertEquals(expectedPositionPage, positionPage);
    }
}
