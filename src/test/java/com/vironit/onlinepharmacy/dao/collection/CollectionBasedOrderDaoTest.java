package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.collection.util.IdGenerator;
import com.vironit.onlinepharmacy.model.*;
import org.junit.jupiter.api.Assertions;
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

    @Mock
    private IdGenerator idGenerator;
    @InjectMocks
    private CollectionBasedOrderDao orderDao;

    private User user;
    private User secondUser;
    private Order order;
    private Order secondOrder;
    private Order thirdOrder;

    @BeforeEach
    void set() {
        user = new User(1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
        secondUser = new User(2, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
        order = new Order(-1, Instant.now(), user, OrderStatus.PREPARATION);
        secondOrder = new Order(-1, Instant.now(), secondUser, OrderStatus.PREPARATION);
        thirdOrder = new Order(-1, Instant.now(), user, OrderStatus.PREPARATION);
    }

    @Test
    void addShouldAddOrderToCollection() {
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
    void getShouldGetOrderFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        long id = orderDao.add(order);

        Order acquiredOrder = orderDao.get(id)
                .get();

        Assertions.assertEquals(order, acquiredOrder);
    }

    @Test
    void addAllGetAllShouldAddAndGetAllOrdersFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        Collection<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(secondOrder);
        orders.add(thirdOrder);
        orderDao.addAll(orders);

        Collection<Order> acquiredOrders = orderDao.getAll();

        Assertions.assertEquals(orders, acquiredOrders);
    }

    @Test
    void updateShouldUpdateOrderInCollection() {
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
    void removeShouldRemoveOrderFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        orderDao.add(order);

        orderDao.remove(0);
        long sizeAfterRemove = orderDao.getAll()
                .size();

        Assertions.assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getAllByOwnerIdShouldReturnAllOrdersOfUser() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
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
    void removeAllByOwnerIdShouldRemoveAllOrdersOfUser() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        orderDao.add(order);
        orderDao.add(secondOrder);
        orderDao.add(thirdOrder);

        orderDao.removeAllByOwnerId(1);

        Collection<Order> actualOrders = orderDao.getAll();
        Collection<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(secondOrder);
        Assertions.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualZero() {
        int totalElements = orderDao.getTotalElements();
        Assertions.assertEquals(0, totalElements);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualTwo() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L);
        orderDao.add(order);
        orderDao.add(secondOrder);
        int totalElements = orderDao.getTotalElements();
        Assertions.assertEquals(2, totalElements);
    }

    @Test
    void getPageShouldReturnASecondPageWithOneElement() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        orderDao.add(order);
        orderDao.add(secondOrder);
        orderDao.add(thirdOrder);

        Collection<Order> orderPage = orderDao.getPage(2, 2);

        Collection<Order> expectedOrderPage = new ArrayList<>();
        expectedOrderPage.add(thirdOrder);
        Assertions.assertEquals(expectedOrderPage, orderPage);
    }

    @Test
    void getPageShouldReturnAThirdPageWithNoElements() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        orderDao.add(order);
        orderDao.add(secondOrder);
        orderDao.add(thirdOrder);

        Collection<Order> orderPage = orderDao.getPage(3, 2);

        Collection<Order> expectedOrderPage = new ArrayList<>();
        Assertions.assertEquals(expectedOrderPage, orderPage);
    }
}
