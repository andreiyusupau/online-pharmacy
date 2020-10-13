package com.vironit.onlinepharmacy.service.order;

import com.vironit.onlinepharmacy.dao.OperationPositionDao;
import com.vironit.onlinepharmacy.dao.OrderDao;
import com.vironit.onlinepharmacy.dto.OrderCreateData;
import com.vironit.onlinepharmacy.model.Order;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.order.exception.OrderException;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.service.stock.StockService;
import com.vironit.onlinepharmacy.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasicOrderServiceTest {
    @Mock
    Collection<Order> orders;
    @Mock
    User user;
    @Mock
    Order order;
    @Mock
    Product product;
    @Mock
    OrderCreateData orderCreateData;
    @Mock
    OrderDao orderDao;
    @Mock
    OperationPositionDao operationPositionDao;
    @Mock
    StockService stockService;
    @Mock
    UserService userService;
    @Mock
    ProductService productService;
@InjectMocks
    BasicOrderService orderService;

    @Test
    void testAdd() {
        when(userService.get(anyLong())).thenReturn(user);
       long id= orderService.add(orderCreateData);
        Assertions.assertEquals(0,id);
    }

    @Test
    void testPayForOrderShouldNotThrowException() {
        when(orderDao.get(anyLong())).thenReturn(Optional.of(order));
        long id=1;

        orderService.payForOrder(id);
    }

    @Test
    void testPayForOrderShouldThrowException() {
        when(orderDao.get(anyLong())).thenReturn(Optional.empty());

        long id=1;
        Exception exception = Assertions.assertThrows(OrderException.class, () -> {
            orderService.payForOrder(id);
        });

        String expectedMessage = "Can't pay for order. Order with id " + id + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testConfirmOrderShouldNotThrowException(){
        when(orderDao.get(anyLong())).thenReturn(Optional.of(order));
        long id=1;

        orderService.confirmOrder(id);
    }

    @Test
    void testConfirmOrderShouldThrowException(){
        when(orderDao.get(anyLong())).thenReturn(Optional.empty());

        long id=1;
        Exception exception = Assertions.assertThrows(OrderException.class, () -> {
            orderService.confirmOrder(id);
        });

        String expectedMessage = "Can't confirm order. Order with id " + id + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCompleteOrderShouldNotThrowException(){
        when(orderDao.get(anyLong())).thenReturn(Optional.of(order));
        long id=1;

        orderService.completeOrder(id);
    }

    @Test
    void testCompleteOrderShouldThrowException(){
        when(orderDao.get(anyLong())).thenReturn(Optional.empty());

        long id=1;
        Exception exception = Assertions.assertThrows(OrderException.class, () -> {
            orderService.completeOrder(id);
        });

        String expectedMessage = "Can't complete order. Order with id " + id + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCancelOrderShouldNotThrowException(){
        when(orderDao.get(anyLong())).thenReturn(Optional.of(order));
        long id=1;

        orderService.cancelOrder(id);
    }

    @Test
    void testCancelOrderShouldThrowException(){
        when(orderDao.get(anyLong())).thenReturn(Optional.empty());

        long id=1;
        Exception exception = Assertions.assertThrows(OrderException.class, () -> {
            orderService.cancelOrder(id);
        });

        String expectedMessage = "Can't cancel order. Order with id " + id + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetShouldNotThrowException() {
        when(orderDao.get(anyLong())).thenReturn(Optional.of(order));
      orderDao.get(anyLong());
    }

    @Test
    void testGetShouldThrowException() {
        when(orderDao.get(anyLong())).thenReturn(Optional.empty());

        long id=1;
        Exception exception = Assertions.assertThrows(OrderException.class, () -> {
            orderDao.get(id);
        });

        String expectedMessage = "Can't get order. Order with id " + id + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void testGetAllShouldNotThrowException() {
        when(orderDao.getAll()).thenReturn(orders);
        orderDao.getAll();
    }

    @Test
    void testUpdate() {
//        OrderUpdateData orderUpdateData=new OrderUpdateData();
//        when(orderService.get(1)).thenReturn();
//
//        Order order = get(orderUpdateData.getId());
//
//       orderService.update();
//        verify(operationPositionDao).removeAllByOwnerId(id);
//        verify(orderDao).remove(id);
    }

    @Test
    void testGetAllByOwnerId(){

    }

    @Test
    void testRemove() {
        User user=new User();
        long id=1;
        orderService.remove(id);
        verify(operationPositionDao).removeAllByOwnerId(id);
        verify(orderDao).remove(id);
    }
}
