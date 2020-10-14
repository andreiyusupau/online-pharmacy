package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.model.Order;
import com.vironit.onlinepharmacy.model.OrderStatus;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollectionBasedOrderDaoTest {

    private static User user;
    @Mock
    private IdGenerator idGenerator;
    @InjectMocks
    private CollectionBasedOrderDao orderDao;
    private Order order;

    @BeforeAll
    static void init() {
        user = new User(1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
    }

    @BeforeEach
    void set() {
        order = new Order(-1, Instant.now(), user, OrderStatus.PREPARATION);
    }

    @Test
    void testAdd() {
        when(idGenerator.getNextId())
                .thenReturn(0L);

        long id = orderDao.add(order);

        long sizeAfterAdd = orderDao.getAll()
                .size();
        Assertions.assertEquals(1, sizeAfterAdd);
        verify(idGenerator, times(1)).getNextId();
        Assertions.assertEquals(0, id);
    }

    @Test
    void testGet() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        long id = orderDao.add(order);

        Order acquiredOrder = orderDao.get(id)
                .get();

        Assertions.assertEquals(order, acquiredOrder);
    }

    @Test
    void testAddAllGetAll() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        Order secondOrder = new Order(-1, Instant.now(), user, OrderStatus.PREPARATION);
        Order thirdOrder = new Order(-1, Instant.now(), user, OrderStatus.PREPARATION);
        Collection<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(secondOrder);
        orders.add(thirdOrder);
        orderDao.addAll(orders);

        Collection<Order> acquiredOrders = orderDao.getAll();

        Assertions.assertEquals(orders, acquiredOrders);
    }

    @Test
    void testUpdate() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        orderDao.add(order);
        Order orderForUpdate = new Order(0, Instant.now(), user, OrderStatus.IN_PROGRESS);

        orderDao.update(orderForUpdate);

        Order updatedOrder = orderDao.get(0)
                .get();

        Assertions.assertEquals(orderForUpdate, updatedOrder);
    }

    @Test
    void testRemove() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        orderDao.add(order);

        orderDao.remove(0);
        long sizeAfterRemove = orderDao.getAll()
                .size();

        Assertions.assertEquals(0, sizeAfterRemove);
    }

    @Test
    void testGetAllByOwnerId() {
        User secondUser = new User(2, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        Order secondOrder = new Order(-1, Instant.now(), secondUser, OrderStatus.PREPARATION);
        Order thirdOrder = new Order(-1, Instant.now(), user, OrderStatus.PREPARATION);
        orderDao.add(order);
        orderDao.add(secondOrder);
        orderDao.add(thirdOrder);

        Collection<Order> actualOrders = orderDao.getAllByOwnerId(1);

        Collection<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(order);
        expectedOrders.add(thirdOrder);
        Assertions.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void testRemoveAllByOwnerId() {
        User secondUser = new User(2, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        Order secondOrder = new Order(-1, Instant.now(), secondUser, OrderStatus.PREPARATION);
        Order thirdOrder = new Order(-1, Instant.now(), user, OrderStatus.PREPARATION);
        orderDao.add(order);
        orderDao.add(secondOrder);
        orderDao.add(thirdOrder);

        orderDao.removeAllByOwnerId(1);

        Collection<Order> actualOrders = orderDao.getAll();
        Collection<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(secondOrder);
        Assertions.assertEquals(expectedOrders, actualOrders);
    }
}
