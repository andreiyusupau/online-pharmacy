package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

class CollectionBasedReserveDaoTest {

    private static Product product;

    private static Operation operation;

    private CollectionBasedReserveDao reserveDao;

    private OperationPosition operationPosition;

    @BeforeAll
    static void init() {
        product = new Product(1, "testProduct", new BigDecimal("100"), null);
        operation = new Order(1, Instant.now(), null,
                OrderStatus.PREPARATION);
    }

    @BeforeEach
    void set() {
        reserveDao = new CollectionBasedReserveDao();
        operationPosition = new OperationPosition(1, 10, product, operation);
    }

    @Test
    void testAdd() {
        long id = reserveDao.add(operationPosition);

        long sizeAfterAdd = reserveDao.getAll().size();
        Assertions.assertEquals(1, sizeAfterAdd);
        Assertions.assertEquals(1, id);
    }

    @Test
    void testGet() {
        long id = reserveDao.add(operationPosition);

        OperationPosition acquiredOperationPosition = reserveDao.get(id)
                .get();

        Assertions.assertEquals(operationPosition, acquiredOperationPosition);
    }

    @Test
    void testAddAllGetAll() {
        Product secondProduct = new Product(2, "secondTestProduct", new BigDecimal("120"), null);
        Product thirdProduct = new Product(3, "thirdTestProduct", new BigDecimal("180"), null);
        OperationPosition secondOperationPosition = new OperationPosition(2, 11, secondProduct, operation);
        OperationPosition thirdOperationPosition = new OperationPosition(3, 14, thirdProduct, operation);
        Collection<OperationPosition> operationPositions = new ArrayList<>();
        operationPositions.add(operationPosition);
        operationPositions.add(secondOperationPosition);
        operationPositions.add(thirdOperationPosition);

        reserveDao.addAll(operationPositions);

        Collection<OperationPosition> acquiredOperationPositions = reserveDao.getAll();
        Assertions.assertEquals(operationPositions, acquiredOperationPositions);
    }

    @Test
    void testUpdate() {
        reserveDao.add(operationPosition);
        OperationPosition operationPositionForUpdate = new OperationPosition(1, 15, product, operation);

        reserveDao.update(operationPositionForUpdate);

        OperationPosition updatedOperationPosition = reserveDao.get(1)
                .get();

        Assertions.assertEquals(operationPositionForUpdate, updatedOperationPosition);
    }

    @Test
    void testRemove() {
        reserveDao.add(operationPosition);

        reserveDao.remove(1);

        long sizeAfterRemove = reserveDao.getAll().size();
        Assertions.assertEquals(0, sizeAfterRemove);
    }

    @Test
    void testGetAllByOwnerId() {
        Operation secondOperation = new Order(2, Instant.now(), null, OrderStatus.PREPARATION);

        OperationPosition secondOperationPosition = new OperationPosition(2, 11, product, secondOperation);
        OperationPosition thirdOperationPosition = new OperationPosition(3, 14, product, operation);
        reserveDao.add(operationPosition);
        reserveDao.add(secondOperationPosition);
        reserveDao.add(thirdOperationPosition);

        Collection<OperationPosition> actualOperationPositions = reserveDao.getAllByOwnerId(1);

        Collection<OperationPosition> expectedOperationPositions = new ArrayList<>();
        expectedOperationPositions.add(operationPosition);
        expectedOperationPositions.add(thirdOperationPosition);
        Assertions.assertEquals(expectedOperationPositions, actualOperationPositions);
    }

    @Test
    void testRemoveAllByOwnerId() {
        Operation secondOperation = new Order(2, Instant.now(), null, OrderStatus.PREPARATION);

        OperationPosition secondOperationPosition = new OperationPosition(-1, 11, product, secondOperation);
        OperationPosition thirdOperationPosition = new OperationPosition(-1, 14, product, operation);
        reserveDao.add(operationPosition);
        reserveDao.add(secondOperationPosition);
        reserveDao.add(thirdOperationPosition);

        reserveDao.removeAllByOwnerId(1);
        Collection<OperationPosition> actualOperationPositions = reserveDao.getAll();

        Collection<OperationPosition> expectedOperationPositions = new ArrayList<>();
        expectedOperationPositions.add(secondOperationPosition);
        Assertions.assertEquals(expectedOperationPositions, actualOperationPositions);
    }
}
