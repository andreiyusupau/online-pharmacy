package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.OrderPositionDao;
import com.vironit.onlinepharmacy.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
class JpaOrderPositionDaoTest {

    @Container
    private PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:13.0-alpine")
            .withDatabaseName("online_pharmacy")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private OrderPositionDao orderPositionDao;

    private ProductCategory productCategory;
    private Product product;
    private Order order;
    private Order secondOrder;
    private OrderPosition orderPosition;
    private OrderPosition secondOrderPosition;
    private OrderPosition thirdOrderPosition;

    @BeforeEach
    void set() {

        product = new Product();
        product.setName("testProduct");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setProductCategory(null);
        product.setRecipeRequired(false);
        order = new Order();
        order.setDate(Instant.now());
        order.setOwner(null);
        order.setStatus(OrderStatus.PREPARATION);
        secondOrder = new Order();
        secondOrder.setDate(Instant.now());
        secondOrder.setOwner(null);
        secondOrder.setStatus(OrderStatus.PREPARATION);
        orderPosition = new OrderPosition();
        orderPosition.setProduct(product);
        orderPosition.setOrder(order);
        orderPosition.setQuantity(5);
        secondOrderPosition = new OrderPosition();
        secondOrderPosition.setProduct(product);
        secondOrderPosition.setOrder(secondOrder);
        secondOrderPosition.setQuantity(11);
        thirdOrderPosition = new OrderPosition();
        thirdOrderPosition.setProduct(product);
        thirdOrderPosition.setOrder(order);
        thirdOrderPosition.setQuantity(14);
    }

    @Test
    void addShouldInsertPositionToCollection() {
        long id = orderPositionDao.add(orderPosition);

        long sizeAfterAdd = orderPositionDao.getAll()
                .size();
        assertEquals(1, sizeAfterAdd);
        assertEquals(1, id);
    }

    @Test
    void getShouldGetPositionFromCollection() {
        long id = orderPositionDao.add(orderPosition);

        OrderPosition acquiredOrderPosition = orderPositionDao.get(id)
                .get();

        assertEquals(orderPosition, acquiredOrderPosition);
    }

    @Test
    void addAllGetAllShouldAddAndGetAllPositionsFromCollection() {
        Collection<OrderPosition> orderPositions = List.of(orderPosition, secondOrderPosition, thirdOrderPosition);

        orderPositionDao.addAll(orderPositions);

        Collection<OrderPosition> acquiredOrderPositions = orderPositionDao.getAll();
        assertEquals(orderPositions, acquiredOrderPositions);
    }

    @Test
    void updateShouldUpdatePositionInCollection() {
        orderPositionDao.add(orderPosition);
        OrderPosition orderPositionForUpdate = new OrderPosition(1, 15, product, order);

        orderPositionDao.update(orderPositionForUpdate);

        OrderPosition updatedOrderPosition = orderPositionDao.get(0)
                .get();
        assertEquals(orderPositionForUpdate, updatedOrderPosition);
    }

    @Test
    void removeShouldRemovePositionFromCollection() {
        orderPositionDao.add(orderPosition);

        orderPositionDao.remove(1);
        long sizeAfterRemove = orderPositionDao.getAll().size();

        assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getAllByOwnerIdShouldGetAllPositionsOfOperation() {
        orderPositionDao.add(orderPosition);
        orderPositionDao.add(secondOrderPosition);
        orderPositionDao.add(thirdOrderPosition);

        Collection<OrderPosition> actualOrderPositions = orderPositionDao.getAllByOwnerId(1);

        Collection<OrderPosition> expectedOrderPositions = List.of(orderPosition, thirdOrderPosition);
        assertEquals(expectedOrderPositions, actualOrderPositions);
    }

    @Test
    void removeAllByOwnerIdShouldRemoveAllPositionsOfOperation() {
        orderPositionDao.add(orderPosition);
        orderPositionDao.add(secondOrderPosition);
        orderPositionDao.add(thirdOrderPosition);

        orderPositionDao.removeAllByOwnerId(1);
        Collection<OrderPosition> actualOrderPositions = orderPositionDao.getAll();

        Collection<OrderPosition> expectedOrderPositions = List.of(secondOrderPosition);
        assertEquals(expectedOrderPositions, actualOrderPositions);
    }
}
