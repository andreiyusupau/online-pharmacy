package com.vironit.onlinepharmacy.service.stock;

import com.vironit.onlinepharmacy.dao.ReserveDao;
import com.vironit.onlinepharmacy.dao.StockDao;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasicStockServiceTest {

    @Mock
    private StockDao stockDao;
    @Mock
    private ReserveDao reserveDao;
    @InjectMocks
    private BasicStockService stockService;

    private Product product;
    private Position position;
    private Product secondProduct;
    private Position secondPosition;
    private Collection<Position> positions;
    private Order order;

    @BeforeEach
    void set() {
        product = new Product(1, "testProduct", new BigDecimal("1421"), null, false);
        position = new Position(1, 2, product);
        secondProduct = new Product(2, "secondTestProduct", new BigDecimal("152"), null, false);
        secondPosition = new Position(2, 51, secondProduct);
        positions = new ArrayList<>();
        positions.add(position);
        positions.add(secondPosition);
        order = new Order(1, Instant.now(), null, OrderStatus.PAID);
    }

    @Test
    void addShouldUseDao() {
        when(stockDao.add(any()))
                .thenReturn(0L);

        long id = stockService.add(position);

        verify(stockDao).add(position);
        Assertions.assertEquals(0, id);
    }

    @Test
    void getShouldNotThrowException() {
        when(stockDao.get(anyLong()))
                .thenReturn(Optional.of(position));

        Position actualPosition = stockService.get(1);

        verify(stockDao).get(1);
        Assertions.assertEquals(position, actualPosition);
    }

    @Test
    void getShouldThrowException() {
        when(stockDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(StockServiceException.class, () -> stockService.get(1));

        verify(stockDao).get(1);
        String expectedMessage = "Can't get stock position. Position with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getAllShouldNotThrowException() {
        when(stockDao.getAll())
                .thenReturn(positions);

        Collection<Position> actualPositions = stockService.getAll();

        Assertions.assertEquals(positions, actualPositions);
    }

    @Test
    void updateShouldUseDao() {
        Position positionForUpdate = new Position(1, 6, secondProduct);
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
        when(stockDao.add(any(Position.class)))
                .thenReturn(1L)
                .thenReturn(2L);
        stockService.addAll(positions);
        verify(stockDao, times(2)).add(any());
    }

    @Test
    void reserveShouldPutDesiredPositionQuantitiesFromStockToReserve() {
        OperationPosition operationPosition = new OperationPosition(1, 2, product, order);
        OperationPosition secondOperationPosition = new OperationPosition(2, 5, secondProduct, order);
        Collection<OperationPosition> operationPositions = new ArrayList<>();
        operationPositions.add(operationPosition);
        operationPositions.add(secondOperationPosition);
        when(stockDao.getByProductId(1))
                .thenReturn(Optional.of(position));
        when(stockDao.getByProductId(2))
                .thenReturn(Optional.of(secondPosition));
        stockService.reserve(operationPositions);
        verify(stockDao, times(2)).getByProductId(anyLong());
        verify(stockDao, times(2)).update(any());
        verify(reserveDao, times(2)).add(any());
    }

    @Test
    void reserveShouldThrowExceptionNotInStock() {
        Product unknownProduct = new Product(3, "unknownProduct", new BigDecimal("124"), null, false);
        OperationPosition operationPosition = new OperationPosition(1, 2, product, order);
        OperationPosition secondOperationPosition = new OperationPosition(2, 5, unknownProduct, order);
        Collection<OperationPosition> operationPositions = new ArrayList<>();
        operationPositions.add(operationPosition);
        operationPositions.add(secondOperationPosition);
        when(stockDao.getByProductId(1))
                .thenReturn(Optional.of(position));
        when(stockDao.getByProductId(3))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(StockServiceException.class,
                () -> stockService.reserve(operationPositions));

        String expectedMessage = "Can't reserve position " + secondOperationPosition.toString() + ", because it's not in stock.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void reserveShouldThrowExceptionNotEnough() {
        OperationPosition operationPosition = new OperationPosition(1, 6, product, order);
        OperationPosition secondOperationPosition = new OperationPosition(2, 5, secondProduct, order);
        Collection<OperationPosition> operationPositions = new ArrayList<>();
        operationPositions.add(operationPosition);
        operationPositions.add(secondOperationPosition);
        when(stockDao.getByProductId(1))
                .thenReturn(Optional.of(position));

        Exception exception = Assertions.assertThrows(StockServiceException.class,
                () -> stockService.reserve(operationPositions));

        String expectedMessage = "Can't reserve position " + operationPosition.toString()
                + ". Desired quantity " + 6 + ", quantity in stock " + 2 + ".";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void takeShouldRemoveOrderPositionsFromReserve() {
        stockService.take(1);
        verify(reserveDao).removeAllByOwnerId(1);
    }

    @Test
    void annulShouldMovePositionsFromReserveToStock() {
        stockService.annul(1);
        verify(reserveDao).getAllByOwnerId(1);
        verify(reserveDao).removeAllByOwnerId(1);
    }
}
