package com.vironit.onlinepharmacy.service.order;

import com.vironit.onlinepharmacy.dao.OperationPositionDao;
import com.vironit.onlinepharmacy.dao.OrderDao;
import com.vironit.onlinepharmacy.dto.OperationPositionData;
import com.vironit.onlinepharmacy.dto.OrderCreateData;
import com.vironit.onlinepharmacy.dto.OrderUpdateData;
import com.vironit.onlinepharmacy.model.*;
import com.vironit.onlinepharmacy.service.order.exception.OrderException;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.service.stock.StockService;
import com.vironit.onlinepharmacy.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasicOrderServiceTest {

    @Mock
    private OrderDao orderDao;
    @Mock
    private OperationPositionDao operationPositionDao;
    @Mock
    private StockService stockService;
    @Mock
    private UserService userService;
    @Mock
    private ProductService productService;
    @InjectMocks
    private BasicOrderService orderService;

    private User user;
    private Product firstProduct;
    private Product secondProduct;
    private Product thirdProduct;
    private Order order;
    private OperationPosition firstOperationPosition;
    private OperationPosition secondOperationPosition;
    private OperationPosition thirdOperationPosition;
    private OperationPositionData firstOperationPositionData;
    private OperationPositionData secondOperationPositionData;
    private OperationPositionData thirdOperationPositionData;
    private List<OperationPositionData> operationPositionDataList;
    private Order secondOrder;
    private Order thirdOrder;
    private Collection<Order> orders;


    @BeforeEach
    void set() {
        user = new User(1, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123", Role.CONSUMER);
        firstProduct = new Product(1, "firstProduct", new BigDecimal("35"), null);
        secondProduct = new Product(2, "secondProduct", new BigDecimal("345"), null);
        thirdProduct = new Product(3, "thirdProduct", new BigDecimal("67"), null);
        order = new Order(1, Instant.now(), user, OrderStatus.PREPARATION);
        firstOperationPosition = new OperationPosition(1, 7, firstProduct, order);
        secondOperationPosition = new OperationPosition(2, 64, secondProduct, order);
        thirdOperationPosition = new OperationPosition(3, 124, thirdProduct, order);
        firstOperationPositionData = new OperationPositionData(1, 10);
        secondOperationPositionData = new OperationPositionData(2, 15);
        thirdOperationPositionData = new OperationPositionData(3, 25);
        operationPositionDataList = new ArrayList<>();
        operationPositionDataList.add(firstOperationPositionData);
        operationPositionDataList.add(secondOperationPositionData);
        operationPositionDataList.add(thirdOperationPositionData);
        secondOrder = new Order(2, Instant.now(), user, OrderStatus.PAID);
        thirdOrder = new Order(3, Instant.now(), user, OrderStatus.IN_PROGRESS);
        orders = new ArrayList<>();
        orders.add(order);
        orders.add(secondOrder);
        orders.add(thirdOrder);
    }

    @Test
    void testAdd() {
        when(userService.get(anyLong()))
                .thenReturn(user);

        OrderCreateData orderCreateData = new OrderCreateData(1, operationPositionDataList);

        long id = orderService.add(orderCreateData);

        Assertions.assertEquals(0, id);
    }

    @Test
    void testPayForOrderShouldNotThrowException() {
        when(orderDao.get(anyLong()))
                .thenReturn(Optional.of(order));

        orderService.payForOrder(1);
    }

    @Test
    void testPayForOrderShouldThrowException() {
        when(orderDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(OrderException.class,
                () -> orderService.payForOrder(1));

        String expectedMessage = "Can't pay for order. Order with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testConfirmOrderShouldNotThrowException() {
        when(orderDao.get(anyLong()))
                .thenReturn(Optional.of(order));

        orderService.confirmOrder(1);
    }

    @Test
    void testConfirmOrderShouldThrowException() {
        when(orderDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(OrderException.class, () -> orderService.confirmOrder(1));

        String expectedMessage = "Can't confirm order. Order with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCompleteOrderShouldNotThrowException() {
        when(orderDao.get(anyLong()))
                .thenReturn(Optional.of(order));

        orderService.completeOrder(1);
    }

    @Test
    void testCompleteOrderShouldThrowException() {
        when(orderDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(OrderException.class, () -> orderService.completeOrder(1));

        String expectedMessage = "Can't complete order. Order with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCancelOrderShouldNotThrowException() {
        when(orderDao.get(anyLong())).thenReturn(Optional.of(order));

        orderService.cancelOrder(1);
    }

    @Test
    void testCancelOrderShouldThrowException() {
        when(orderDao.get(anyLong())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(OrderException.class, () -> orderService.cancelOrder(1));

        String expectedMessage = "Can't cancel order. Order with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetShouldNotThrowException() {
        when(orderDao.get(anyLong()))
                .thenReturn(Optional.of(order));
        Order actualOrder = orderDao.get(1)
                .get();
        Assertions.assertEquals(order, actualOrder);
    }

    @Test
    void testGetShouldThrowException() {
        when(orderDao.get(anyLong())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(OrderException.class, () -> orderService.get(1));

        String expectedMessage = "Can't get order. Order with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetAllShouldNotThrowException() {

        when(orderDao.getAll())
                .thenReturn(orders);

        Collection<Order> actualOrders = orderService.getAll();

        Assertions.assertEquals(orders, actualOrders);
    }

    @Test
    void testUpdate() {
        OrderUpdateData orderUpdateData = new OrderUpdateData(1, 1, operationPositionDataList);
        when(orderDao.get(1))
                .thenReturn(Optional.of(order));

        orderService.update(orderUpdateData);

        verify(operationPositionDao).removeAllByOwnerId(1);
        verify(operationPositionDao).addAll(any());
    }

    @Test
    void testGetOrdersByUserId() {
        when(orderDao.getAllByOwnerId(anyLong()))
                .thenReturn(orders);

        Collection<Order> actualOrders = orderService.getOrdersByUserId(1);

        verify(orderDao).getAllByOwnerId(1);
        Assertions.assertEquals(orders, actualOrders);
    }

    @Test
    void testRemove() {
        when(orderDao.remove(anyLong()))
                .thenReturn(true);
        when(operationPositionDao.removeAllByOwnerId(anyLong()))
                .thenReturn(true);

        orderService.remove(1);

        verify(operationPositionDao).removeAllByOwnerId(1);
        verify(orderDao).remove(1);
    }
}
