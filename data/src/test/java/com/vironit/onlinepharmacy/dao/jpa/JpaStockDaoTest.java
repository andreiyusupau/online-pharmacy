package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.StockDao;
import com.vironit.onlinepharmacy.model.Position;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.StockPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
class JpaStockDaoTest {

    @Container
    private PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:13.0-alpine")
            .withDatabaseName("online_pharmacy")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private StockDao stockDao;

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
        position = new StockPosition();
        position.setQuantity(10);
        position.setProduct(product);
        position.setReservedQuantity(0);
        secondPosition = new StockPosition();
        secondPosition.setQuantity(1);
        secondPosition.setProduct(secondProduct);
        secondPosition.setReservedQuantity(0);
        thirdPosition = new StockPosition();
        thirdPosition.setQuantity(14);
        thirdPosition.setProduct(thirdProduct);
        thirdPosition.setReservedQuantity(0);
    }

    @Test
    void getByProductIdShouldReturnPositionWithProductId() {
        stockDao.add(position);

        Position acquiredPosition = stockDao.getByProductId(1)
                .get();

        assertEquals(position, acquiredPosition);
    }

    @Test
    void addShouldAddPositionToCollection() {
        long id = stockDao.add(position);

        long sizeAfterAdd = stockDao.getAll()
                .size();
        assertEquals(1, sizeAfterAdd);
        assertEquals(1, id);
    }

    @Test
    void getShouldGetPositionFromCollection() {
        long id = stockDao.add(position);

        Position acquiredPosition = stockDao.get(id)
                .get();

        assertEquals(position, acquiredPosition);
    }

    @Test
    void getAllShouldGetAllPositionsFromCollection() {
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
        stockDao.add(position);
        StockPosition positionForUpdate = new StockPosition(1, 15, product, 0);

        stockDao.update(positionForUpdate);

        StockPosition updatedPosition = stockDao.get(1)
                .get();

        assertEquals(positionForUpdate, updatedPosition);
    }

    @Test
    void removeShouldRemovePositionFromCollection() {
        stockDao.add(position);

        stockDao.remove(1);

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
        stockDao.add(position);
        stockDao.add(secondPosition);

        long totalElements = stockDao.getTotalElements();

        assertEquals(2, totalElements);
    }

    @Test
    void getPageShouldReturnASecondPageWithOneElement() {
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
        stockDao.add(position);
        stockDao.add(secondPosition);
        stockDao.add(thirdPosition);

        Collection<StockPosition> positionPage = stockDao.getPage(3, 2);

        Collection<StockPosition> expectedPositionPage = new ArrayList<>();
        assertEquals(expectedPositionPage, positionPage);
    }
}
