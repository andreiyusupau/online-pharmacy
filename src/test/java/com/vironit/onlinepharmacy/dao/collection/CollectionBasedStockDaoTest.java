package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.model.Position;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CollectionBasedStockDaoTest {

    @Mock
    private IdGenerator idGenerator;
    @InjectMocks
    private CollectionBasedStockDao stockDao;

    private Product product;
    private Position position;

    @BeforeEach
    void set() {
        product = new Product(1, "testProduct", new BigDecimal("100"), null,false);
        position = new Position(1, 10, product);
    }

    @Test
    void getByProductIdShouldReturnPositionWithProductId() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        stockDao.add(position);

        Position acquiredPosition = stockDao.getByProductId(1)
                .get();

        Assertions.assertEquals(position, acquiredPosition);
    }

    @Test
    void addShouldAddPositionToCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        long id = stockDao.add(position);

        long sizeAfterAdd = stockDao.getAll()
                .size();
        Assertions.assertEquals(1, sizeAfterAdd);
        Assertions.assertEquals(0, id);
    }

    @Test
    void getShouldGetPositionFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        long id = stockDao.add(position);

        Position acquiredPosition = stockDao.get(id)
                .get();

        Assertions.assertEquals(position, acquiredPosition);
    }

    @Test
    void getAllShouldGetAllPositionsFromCollection() {
        Product secondProduct = new Product(2, "secondTestProduct", new BigDecimal("120"), null,false);
        Product thirdProduct = new Product(3, "thirdTestProduct", new BigDecimal("180"), null,false);
        Position secondPosition = new Position(2, 11, secondProduct);
        Position thirdPosition = new Position(3, 14, thirdProduct);

        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        stockDao.add(position);
        stockDao.add(secondPosition);
        stockDao.add(thirdPosition);

        Collection<Position> acquiredPositions = stockDao.getAll();

        Collection<Position> expectedPositions = new ArrayList<>();
        expectedPositions.add(position);
        expectedPositions.add(secondPosition);
        expectedPositions.add(thirdPosition);
        Assertions.assertEquals(expectedPositions, acquiredPositions);
    }

    @Test
    void updateShouldUpdatePositionInCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        stockDao.add(position);
        Position positionForUpdate = new Position(0, 15, product);

        stockDao.update(positionForUpdate);

        Position updatedPosition = stockDao.get(0)
                .get();

        Assertions.assertEquals(positionForUpdate, updatedPosition);
    }

    @Test
    void removeShouldRemovePositionFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        stockDao.add(position);

        stockDao.remove(0);

        long sizeAfterRemove = stockDao.getAll()
                .size();
        Assertions.assertEquals(0, sizeAfterRemove);
    }
}
