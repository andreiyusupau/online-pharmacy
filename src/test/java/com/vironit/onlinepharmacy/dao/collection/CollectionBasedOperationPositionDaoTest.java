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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollectionBasedOperationPositionDaoTest {

    private Product product;
    private Operation operation;
    private OperationPosition operationPosition;
    @Mock
    private IdGenerator idGenerator;
    @InjectMocks
    private CollectionBasedOperationPositionDao operationPositionDao;

    @BeforeEach
    void set() {
        product = new Product(1, "testProduct", new BigDecimal("100"), null,false);
        operation = new Procurement(1, Instant.now(), null,
                ProcurementStatus.PREPARATION);
        operationPosition = new OperationPosition(-1, 10, product, operation);
    }

    @Test
    void addShouldInsertPositionToCollection() {
        when(idGenerator.getNextId()).thenReturn(0L);

        long id = operationPositionDao.add(operationPosition);

        long sizeAfterAdd = operationPositionDao.getAll().size();
        Assertions.assertEquals(1, sizeAfterAdd);
        verify(idGenerator, times(1)).getNextId();
        Assertions.assertEquals(0, id);
    }

    @Test
    void getShouldGetPositionFromCollection() {
        when(idGenerator.getNextId()).thenReturn(0L);
        long id = operationPositionDao.add(operationPosition);

        OperationPosition acquiredOperationPosition = operationPositionDao.get(id)
                .get();

        Assertions.assertEquals(operationPosition, acquiredOperationPosition);
    }

    @Test
    void addAllGetAllShouldAddAndGetAllPositionsFromCollection() {
        Operation secondOperation = new Procurement();
        Operation thirdOperation = new Procurement();
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        OperationPosition secondOperationPosition = new OperationPosition(-1, 11, product, secondOperation);
        OperationPosition thirdOperationPosition = new OperationPosition(-1, 14, product, thirdOperation);
        Collection<OperationPosition> operationPositions = new ArrayList<>();
        operationPositions.add(operationPosition);
        operationPositions.add(secondOperationPosition);
        operationPositions.add(thirdOperationPosition);

        operationPositionDao.addAll(operationPositions);

        Collection<OperationPosition> acquiredOperationPositions = operationPositionDao.getAll();
        Assertions.assertEquals(operationPositions, acquiredOperationPositions);
    }

    @Test
    void updateShouldUpdatePositionInCollection() {
        when(idGenerator.getNextId()).thenReturn(0L);
        operationPositionDao.add(operationPosition);
        OperationPosition operationPositionForUpdate = new OperationPosition(0, 15, product, operation);

        operationPositionDao.update(operationPositionForUpdate);

        OperationPosition updatedOperationPosition = operationPositionDao.get(0)
                .get();

        Assertions.assertEquals(operationPositionForUpdate, updatedOperationPosition);
    }

    @Test
    void removeShouldRemovePositionFromCollection() {
        when(idGenerator.getNextId()).thenReturn(0L);
        operationPositionDao.add(operationPosition);

        operationPositionDao.remove(0);
        long sizeAfterRemove = operationPositionDao.getAll().size();

        Assertions.assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getAllByOwnerIdShouldGetAllPositionsOfOperation() {
        Operation secondOperation = new Procurement(2, Instant.now(), null, ProcurementStatus.PREPARATION);
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        OperationPosition secondOperationPosition = new OperationPosition(-1, 11, product, secondOperation);
        OperationPosition thirdOperationPosition = new OperationPosition(-1, 14, product, operation);
        operationPositionDao.add(operationPosition);
        operationPositionDao.add(secondOperationPosition);
        operationPositionDao.add(thirdOperationPosition);

        Collection<OperationPosition> actualOperationPositions = operationPositionDao.getAllByOwnerId(1);

        Collection<OperationPosition> expectedOperationPositions = new ArrayList<>();
        expectedOperationPositions.add(operationPosition);
        expectedOperationPositions.add(thirdOperationPosition);
        Assertions.assertEquals(expectedOperationPositions, actualOperationPositions);
    }

    @Test
    void removeAllByOwnerIdShouldRemoveAllPositionsOfOperation() {
        Operation secondOperation = new Procurement(2, Instant.now(), null, ProcurementStatus.PREPARATION);
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        OperationPosition secondOperationPosition = new OperationPosition(-1, 11, product, secondOperation);
        OperationPosition thirdOperationPosition = new OperationPosition(-1, 14, product, operation);
        operationPositionDao.add(operationPosition);
        operationPositionDao.add(secondOperationPosition);
        operationPositionDao.add(thirdOperationPosition);

        operationPositionDao.removeAllByOwnerId(1);
        Collection<OperationPosition> actualOperationPositions = operationPositionDao.getAll();

        Collection<OperationPosition> expectedOperationPositions = new ArrayList<>();
        expectedOperationPositions.add(secondOperationPosition);
        Assertions.assertEquals(expectedOperationPositions, actualOperationPositions);
    }
}
