package com.vironit.onlinepharmacy.service.order;

import com.vironit.onlinepharmacy.dao.OrderDao;
import com.vironit.onlinepharmacy.dao.OrderPositionDao;
import com.vironit.onlinepharmacy.dto.OrderDto;
import com.vironit.onlinepharmacy.dto.PositionDto;
import com.vironit.onlinepharmacy.model.*;
import com.vironit.onlinepharmacy.service.exception.OrderServiceException;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.service.stock.StockService;
import com.vironit.onlinepharmacy.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
@Disabled
@ExtendWith(MockitoExtension.class)
public class BasicOrderServiceTest {

    @Mock
    private OrderDao orderDao;
    @Mock
    private OrderPositionDao orderPositionDao;
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
    private OrderPosition firstOrderPosition;
    private OrderPosition secondOrderPosition;
    private OrderPosition thirdOrderPosition;
    private PositionDto firstOperationPositionDto;
    private PositionDto secondOperationPositionDto;
    private PositionDto thirdOperationPositionDto;
    private List<PositionDto> operationPositionDtoList;
    private Order secondOrder;
    private Order thirdOrder;
    private Collection<Order> orders;


    @BeforeEach
    void set() {
        user = new User(1, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123", Role.CONSUMER);
        firstProduct = new Product(1, "firstProduct", new BigDecimal("35"), null, false);
        secondProduct = new Product(2, "secondProduct", new BigDecimal("345"), null, false);
        thirdProduct = new Product(3, "thirdProduct", new BigDecimal("67"), null, false);
        order = new Order(1, Instant.now(), user, OrderStatus.PREPARATION);
        firstOrderPosition = new OrderPosition(1, 7, firstProduct, order);
        secondOrderPosition = new OrderPosition(2, 64, secondProduct, order);
        thirdOrderPosition = new OrderPosition(3, 124, thirdProduct, order);
        firstOperationPositionDto = new PositionDto( 1, 10);
        secondOperationPositionDto = new PositionDto( 2, 15);
        thirdOperationPositionDto = new PositionDto( 3, 25);
        operationPositionDtoList = new ArrayList<>();
        operationPositionDtoList.add(firstOperationPositionDto);
        operationPositionDtoList.add(secondOperationPositionDto);
        operationPositionDtoList.add(thirdOperationPositionDto);
        secondOrder = new Order(2, Instant.now(), user, OrderStatus.PAID);
        thirdOrder = new Order(3, Instant.now(), user, OrderStatus.IN_PROGRESS);
        orders = new ArrayList<>();
        orders.add(order);
        orders.add(secondOrder);
        orders.add(thirdOrder);
    }

    @Test
    void addShouldUseDao() {
        when(userService.get(anyLong()))
                .thenReturn(user);

        OrderDto orderDto = new OrderDto( 1, operationPositionDtoList);

        long id = orderService.add(orderDto);

        Assertions.assertEquals(0, id);
    }

    @Test
    void payForOrderShouldNotThrowException() {
        when(orderDao.get(anyLong()))
                .thenReturn(Optional.of(order));

        orderService.payForOrder(1);
    }

    @Test
    void payForOrderShouldThrowException() {
        when(orderDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(OrderServiceException.class,
                () -> orderService.payForOrder(1));

        String expectedMessage = "Can't pay for order. Order with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void confirmOrderShouldNotThrowException() {
        when(orderDao.get(anyLong()))
                .thenReturn(Optional.of(order));

        orderService.confirmOrder(1);
    }

    @Test
    void confirmOrderShouldThrowException() {
        when(orderDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(OrderServiceException.class, () -> orderService.confirmOrder(1));

        String expectedMessage = "Can't confirm order. Order with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void completeOrderShouldNotThrowException() {
        when(orderDao.get(anyLong()))
                .thenReturn(Optional.of(order));

        orderService.completeOrder(1);
    }

    @Test
    void completeOrderShouldThrowException() {
        when(orderDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(OrderServiceException.class, () -> orderService.completeOrder(1));

        String expectedMessage = "Can't complete order. Order with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void cancelOrderShouldNotThrowException() {
        when(orderDao.get(anyLong())).thenReturn(Optional.of(order));

        orderService.cancelOrder(1);
    }

    @Test
    void cancelOrderShouldThrowException() {
        when(orderDao.get(anyLong())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(OrderServiceException.class, () -> orderService.cancelOrder(1));

        String expectedMessage = "Can't cancel order. Order with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getShouldNotThrowException() {
        when(orderDao.get(anyLong()))
                .thenReturn(Optional.of(order));
        Order actualOrder = orderDao.get(1)
                .get();
        Assertions.assertEquals(order, actualOrder);
    }

    @Test
    void getShouldThrowException() {
        when(orderDao.get(anyLong())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(OrderServiceException.class, () -> orderService.get(1));

        String expectedMessage = "Can't get order. Order with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getAllShouldNotThrowException() {

        when(orderDao.getAll())
                .thenReturn(orders);

        Collection<Order> actualOrders = orderService.getAll();

        Assertions.assertEquals(orders, actualOrders);
    }

    @Test
    void updateShouldUseDao() {
        OrderDto orderUpdateData = new OrderDto( 1, operationPositionDtoList);
        orderUpdateData.setId(1);
        when(orderDao.get(1))
                .thenReturn(Optional.of(order));

        orderService.update(orderUpdateData);

        verify(orderPositionDao).removeAllByOwnerId(1);
        verify(orderPositionDao).addAll(any());
    }

    @Test
    void getOrdersByUserId() {
        when(orderDao.getAllByOwnerId(anyLong()))
                .thenReturn(orders);

        Collection<Order> actualOrders = orderService.getAllByOwnerId(1);

        verify(orderDao).getAllByOwnerId(1);
        Assertions.assertEquals(orders, actualOrders);
    }

    @Test
    void removeShouldUseDao() {
        when(orderDao.remove(anyLong()))
                .thenReturn(true);
        when(orderPositionDao.removeAllByOwnerId(anyLong()))
                .thenReturn(true);

        orderService.remove(1);

        verify(orderPositionDao).removeAllByOwnerId(1);
        verify(orderDao).remove(1);
    }
}
