package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.collection.util.IdGenerator;
import com.vironit.onlinepharmacy.model.Order;
import com.vironit.onlinepharmacy.model.OrderPosition;
import com.vironit.onlinepharmacy.model.OrderStatus;
import com.vironit.onlinepharmacy.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollectionBasedOrderPositionDaoTest {

    private Product product;
    private Order order;
    private OrderPosition orderPosition;
    @Mock
    private IdGenerator idGenerator;
    @InjectMocks
    private CollectionBasedOrderPositionDao orderPositionDao;

    @BeforeEach
    void set() {
        product = new Product(1, "testProduct", new BigDecimal("100"), null, false);
        order = new Order(1, Instant.now(), null,
                OrderStatus.PREPARATION);
        orderPosition = new OrderPosition(-1, 10, product, order);
    }

    @Test
    void addShouldInsertPositionToCollection() {
        when(idGenerator.getNextId()).thenReturn(0L);

        long id = orderPositionDao.add(orderPosition);

        long sizeAfterAdd = orderPositionDao.getAll().size();
        Assertions.assertEquals(1, sizeAfterAdd);
        verify(idGenerator, times(1)).getNextId();
        Assertions.assertEquals(0, id);
    }

    @Test
    void getShouldGetPositionFromCollection() {
        when(idGenerator.getNextId()).thenReturn(0L);
        long id = orderPositionDao.add(orderPosition);

        OrderPosition acquiredOrderPosition = orderPositionDao.get(id)
                .get();

        Assertions.assertEquals(orderPosition, acquiredOrderPosition);
    }

    @Test
    void addAllGetAllShouldAddAndGetAllPositionsFromCollection() {
        Order secondOrder = new Order();
        Order thirdOrder = new Order();
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        OrderPosition secondOrderPosition = new OrderPosition(-1, 11, product, secondOrder);
        OrderPosition thirdOrderPosition = new OrderPosition(-1, 14, product, thirdOrder);
        Collection<OrderPosition> orderPositions = List.of(orderPosition, secondOrderPosition, thirdOrderPosition);

        orderPositionDao.addAll(orderPositions);

        Collection<OrderPosition> acquiredOrderPositions = orderPositionDao.getAll();
        Assertions.assertEquals(orderPositions, acquiredOrderPositions);
    }

    @Test
    void updateShouldUpdatePositionInCollection() {
        when(idGenerator.getNextId()).thenReturn(0L);
        orderPositionDao.add(orderPosition);
        OrderPosition orderPositionForUpdate = new OrderPosition(0, 15, product, order);

        orderPositionDao.update(orderPositionForUpdate);

        OrderPosition updatedOrderPosition = orderPositionDao.get(0)
                .get();

        Assertions.assertEquals(orderPositionForUpdate, updatedOrderPosition);
    }

    @Test
    void removeShouldRemovePositionFromCollection() {
        when(idGenerator.getNextId()).thenReturn(0L);
        orderPositionDao.add(orderPosition);

        orderPositionDao.remove(0);
        long sizeAfterRemove = orderPositionDao.getAll().size();

        Assertions.assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getAllByOwnerIdShouldGetAllPositionsOfOperation() {
        Order secondOrder = new Order(2, Instant.now(), null, OrderStatus.PREPARATION);
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        OrderPosition secondOrderPosition = new OrderPosition(-1, 11, product, secondOrder);
        OrderPosition thirdOrderPosition = new OrderPosition(-1, 14, product, order);
        orderPositionDao.add(orderPosition);
        orderPositionDao.add(secondOrderPosition);
        orderPositionDao.add(thirdOrderPosition);

        Collection<OrderPosition> actualOrderPositions = orderPositionDao.getAllByOwnerId(1);

        Collection<OrderPosition> expectedOrderPositions = List.of(orderPosition, thirdOrderPosition);
        Assertions.assertEquals(expectedOrderPositions, actualOrderPositions);
    }

    @Test
    void removeAllByOwnerIdShouldRemoveAllPositionsOfOperation() {
        Order secondOrder = new Order(2, Instant.now(), null, OrderStatus.PREPARATION);
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        OrderPosition secondOrderPosition = new OrderPosition(-1, 11, product, secondOrder);
        OrderPosition thirdOrderPosition = new OrderPosition(-1, 14, product, order);
        orderPositionDao.add(orderPosition);
        orderPositionDao.add(secondOrderPosition);
        orderPositionDao.add(thirdOrderPosition);

        orderPositionDao.removeAllByOwnerId(1);
        Collection<OrderPosition> actualOrderPositions = orderPositionDao.getAll();

        Collection<OrderPosition> expectedOrderPositions = List.of(secondOrderPosition);
        Assertions.assertEquals(expectedOrderPositions, actualOrderPositions);
    }
}
