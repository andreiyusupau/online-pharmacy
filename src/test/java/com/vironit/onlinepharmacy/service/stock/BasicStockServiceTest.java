package com.vironit.onlinepharmacy.service.stock;

import com.vironit.onlinepharmacy.dao.StockDao;
import com.vironit.onlinepharmacy.dto.PositionData;
import com.vironit.onlinepharmacy.model.*;
import com.vironit.onlinepharmacy.service.exception.StockServiceException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasicStockServiceTest {

    @Mock
    private StockDao stockDao;
    @InjectMocks
    private BasicStockService stockService;

    private Product product;
    private Product secondProduct;
    private PositionData positionData;
    private PositionData secondPositionData;
    private Collection<PositionData> positionDataCollection;
    private StockPosition stockPosition;
    private StockPosition secondStockPosition;
    private Collection<StockPosition> stockPositions;
    private Order order;
    private OrderPosition orderPosition;
    private OrderPosition secondOrderPosition;
    private Collection<OrderPosition> orderPositions;


    @BeforeEach
    void set() {
        product = new Product(1, "testProduct", new BigDecimal("1421"), null, false);
        positionData = new PositionData(1, 2);
        stockPosition = new StockPosition(1, 2, product, 0);
        secondProduct = new Product(2, "secondTestProduct", new BigDecimal("152"), null, false);
        secondPositionData = new PositionData(2, 51);
        secondStockPosition = new StockPosition(2, 51, secondProduct, 0);
        stockPositions = List.of(stockPosition, secondStockPosition);
        positionDataCollection = List.of(positionData, secondPositionData);
        order = new Order(1, Instant.now(), null, OrderStatus.PAID);
        orderPosition = new OrderPosition(1, 6, product, order);
        secondOrderPosition = new OrderPosition(2, 5, secondProduct, order);
        orderPositions = List.of(orderPosition, secondOrderPosition);
    }

    @Test
    void addShouldUseDao() {
        when(stockDao.add(any()))
                .thenReturn(0L);

        long id = stockService.add(positionData);

        verify(stockDao, times(1)).add(any(StockPosition.class));
        assertEquals(0, id);
    }

    @Test
    void getShouldNotThrowException() {
        when(stockDao.get(anyLong()))
                .thenReturn(Optional.of(stockPosition));

        StockPosition actualPosition = stockService.get(1);

        verify(stockDao).get(1);
        assertEquals(stockPosition, actualPosition);
    }

    @Test
    void getShouldThrowException() {
        when(stockDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(StockServiceException.class, () -> stockService.get(1));

        verify(stockDao).get(1);
        String expectedMessage = "Can't get stock position. Position with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getAllShouldNotThrowException() {
        when(stockDao.getAll())
                .thenReturn(stockPositions);

        Collection<StockPosition> actualPositions = stockService.getAll();

        assertEquals(stockPositions, actualPositions);
    }

    @Test
    void updateShouldUseDao() {
        StockPosition positionForUpdate = new StockPosition(1, 5, product, 2);
        when(stockDao.update(any()))
                .thenReturn(true);

        stockService.update(positionForUpdate);

        verify(stockDao).update(positionForUpdate);
    }

    @Test
    void removeShouldUseDao() {
        when(stockDao.remove(anyLong()))
                .thenReturn(true);

        stockService.remove(1);

        verify(stockDao).remove(1);
    }

    @Test
    void addAllShouldUseDao() {
        when(stockDao.add(any(StockPosition.class)))
                .thenReturn(1L)
                .thenReturn(2L);
        stockService.addAll(positionDataCollection);
        verify(stockDao, times(2)).add(any());
    }

    @Test
    void reserveShouldPutDesiredPositionQuantitiesFromStockToReserve() {
        when(stockDao.getByProductId(1))
                .thenReturn(Optional.of(stockPosition));
        when(stockDao.getByProductId(2))
                .thenReturn(Optional.of(secondStockPosition));

        stockService.reserveInStock(orderPositions);

        verify(stockDao, times(2)).getByProductId(anyLong());
        verify(stockDao, times(2)).update(any());
    }

    @Test
    void reserveShouldThrowExceptionNotInStock() {
        Product unknownProduct = new Product(3, "unknownProduct", new BigDecimal("124"), null, false);

        OrderPosition secondOrderPosition = new OrderPosition(2, 5, unknownProduct, order);
        Collection<OrderPosition> orderPositions = List.of(orderPosition, secondOrderPosition);
        when(stockDao.getByProductId(1))
                .thenReturn(Optional.of(stockPosition));
        when(stockDao.getByProductId(3))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(StockServiceException.class,
                () -> stockService.reserveInStock(orderPositions));

        String expectedMessage = "Can't reserveInStock position " + secondOrderPosition.toString() + ", because it's not in stock.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void reserveShouldThrowExceptionNotEnough() {
        when(stockDao.getByProductId(1))
                .thenReturn(Optional.of(stockPosition));

        Exception exception = Assertions.assertThrows(StockServiceException.class,
                () -> stockService.reserveInStock(orderPositions));

        String expectedMessage = "Can't reserveInStock position " + orderPosition.toString()
                + ". Desired quantity " + 6 + ", quantity in stock " + 2 + ".";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void takeShouldRemoveOrderPositionsFromReserve() {
        stockService.takeFromStock(orderPositions);
    }

    @Test
    void annulShouldMovePositionsFromReserveToStock() {
        stockService.annulReservationInStock(orderPositions);
    }
}
