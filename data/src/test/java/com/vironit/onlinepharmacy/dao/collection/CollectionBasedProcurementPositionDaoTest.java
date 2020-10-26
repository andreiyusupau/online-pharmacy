package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.collection.util.IdGenerator;
import com.vironit.onlinepharmacy.model.Procurement;
import com.vironit.onlinepharmacy.model.ProcurementPosition;
import com.vironit.onlinepharmacy.model.ProcurementStatus;
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
class CollectionBasedProcurementPositionDaoTest {

    private Product product;
    private Procurement procurement;
    private ProcurementPosition procurementPosition;
    private Procurement secondProcurement;
    private ProcurementPosition secondProcurementPosition;
    private ProcurementPosition thirdProcurementPosition;
    @Mock
    private IdGenerator idGenerator;
    @InjectMocks
    private CollectionBasedProcurementPositionDao procurementPositionDao;

    @BeforeEach
    void set() {
        product = new Product(1, "testProduct", new BigDecimal("100"), null, false);
        procurement = new Procurement(1, Instant.now(), null,
                ProcurementStatus.PREPARATION);
        procurementPosition = new ProcurementPosition(-1, 10, product, procurement);
        secondProcurement = new Procurement(2, Instant.now(), null, ProcurementStatus.PREPARATION);
        secondProcurementPosition = new ProcurementPosition(-1, 11, product, secondProcurement);
        thirdProcurementPosition = new ProcurementPosition(-1, 14, product, procurement);
    }

    @Test
    void addShouldInsertPositionToCollection() {
        when(idGenerator.getNextId()).thenReturn(0L);

        long id = procurementPositionDao.add(procurementPosition);

        long sizeAfterAdd = procurementPositionDao.getAll().size();
        Assertions.assertEquals(1, sizeAfterAdd);
        verify(idGenerator, times(1)).getNextId();
        Assertions.assertEquals(0, id);
    }

    @Test
    void getShouldGetPositionFromCollection() {
        when(idGenerator.getNextId()).thenReturn(0L);
        long id = procurementPositionDao.add(procurementPosition);

        ProcurementPosition acquiredProcurementPosition = procurementPositionDao.get(id)
                .get();

        Assertions.assertEquals(procurementPosition, acquiredProcurementPosition);
    }

    @Test
    void addAllGetAllShouldAddAndGetAllPositionsFromCollection() {
        Procurement thirdProcurement = new Procurement();
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);

        ProcurementPosition thirdProcurementPosition = new ProcurementPosition(-1, 14, product, thirdProcurement);
        Collection<ProcurementPosition> procurementPositions = List.of(procurementPosition, secondProcurementPosition, thirdProcurementPosition);

        procurementPositionDao.addAll(procurementPositions);

        Collection<ProcurementPosition> acquiredProcurementPositions = procurementPositionDao.getAll();
        Assertions.assertEquals(procurementPositions, acquiredProcurementPositions);
    }

    @Test
    void updateShouldUpdatePositionInCollection() {
        when(idGenerator.getNextId()).thenReturn(0L);
        procurementPositionDao.add(procurementPosition);
        ProcurementPosition procurementPositionForUpdate = new ProcurementPosition(0, 15, product, procurement);

        procurementPositionDao.update(procurementPositionForUpdate);

        ProcurementPosition updatedProcurementPosition = procurementPositionDao.get(0)
                .get();

        Assertions.assertEquals(procurementPositionForUpdate, updatedProcurementPosition);
    }

    @Test
    void removeShouldRemovePositionFromCollection() {
        when(idGenerator.getNextId()).thenReturn(0L);
        procurementPositionDao.add(procurementPosition);

        procurementPositionDao.remove(0);
        long sizeAfterRemove = procurementPositionDao.getAll().size();

        Assertions.assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getAllByOwnerIdShouldGetAllPositionsOfOperation() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        procurementPositionDao.add(procurementPosition);
        procurementPositionDao.add(secondProcurementPosition);
        procurementPositionDao.add(thirdProcurementPosition);

        Collection<ProcurementPosition> actualProcurementPositions = procurementPositionDao.getAllByOwnerId(1);

        Collection<ProcurementPosition> expectedProcurementPositions = List.of(procurementPosition, thirdProcurementPosition);
        Assertions.assertEquals(expectedProcurementPositions, actualProcurementPositions);
    }

    @Test
    void removeAllByOwnerIdShouldRemoveAllPositionsOfOperation() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        procurementPositionDao.add(procurementPosition);
        procurementPositionDao.add(secondProcurementPosition);
        procurementPositionDao.add(thirdProcurementPosition);

        procurementPositionDao.removeAllByOwnerId(1);
        Collection<ProcurementPosition> actualProcurementPositions = procurementPositionDao.getAll();

        Collection<ProcurementPosition> expectedProcurementPositions = List.of(secondProcurementPosition);
        Assertions.assertEquals(expectedProcurementPositions, actualProcurementPositions);
    }
}
