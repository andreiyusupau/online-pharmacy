package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.model.Operation;
import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CollectionBasedOperationPositionDaoTest {
    @Mock
    Product product;
    @Mock
    Product secondProduct;
    @Mock
    Product thirdProduct;
    @Mock
    Operation operation;
    @Mock
    Operation secondOperation;
    @Mock
    Operation thirdOperation;
    @Mock
    BasicIdGenerator idGenerator;
    @InjectMocks
    CollectionBasedOperationPositionDao collectionBasedOperationPositionDao;

  @Test
    void testAdd() {
      when(idGenerator.getNextId()).thenReturn(0L);
      OperationPosition operationPosition=new OperationPosition(-1,10,product,operation);

      long id=collectionBasedOperationPositionDao.add(operationPosition);

      Assertions.assertEquals(0, id);
    }

    @Test
    void testGet() {
        when(idGenerator.getNextId()).thenReturn(0L);
        OperationPosition operationPosition=new OperationPosition(-1,10,product,operation);
        long id=collectionBasedOperationPositionDao.add(operationPosition);

        OperationPosition acquiredOperationPosition=collectionBasedOperationPositionDao.get(id)
                .get();

        Assertions.assertEquals(operationPosition,acquiredOperationPosition);
    }

    @Test
    void testAddAllGetAll() {
        when(idGenerator.getNextId()).thenReturn(0L);
        OperationPosition operationPosition=new OperationPosition(-1,10,product,operation);
        OperationPosition secondOperationPosition=new OperationPosition(-1,11,product,secondOperation);
        OperationPosition thirdOperationPosition=new OperationPosition(-1,14,product,thirdOperation);
        Collection<OperationPosition> operationPositions=new ArrayList<>();
        operationPositions.add(operationPosition);
        operationPositions.add(secondOperationPosition);
        operationPositions.add(thirdOperationPosition);
        collectionBasedOperationPositionDao.addAll(operationPositions);

        Collection<OperationPosition> acquiredOperationPositions=collectionBasedOperationPositionDao.getAll();
//TODO:
        Assertions.assertEquals(operationPositions,acquiredOperationPositions);
  }

    @Test
    void testUpdate() {
        when(idGenerator.getNextId()).thenReturn(0L);
        OperationPosition operationPosition=new OperationPosition(-1,10,product,operation);
        collectionBasedOperationPositionDao.add(operationPosition);
        OperationPosition operationPositionForUpdate=new OperationPosition(0,15,product,operation);
        collectionBasedOperationPositionDao.update(operationPositionForUpdate);

        OperationPosition updatedOperationPosition=collectionBasedOperationPositionDao.get(0)
                .get();

        Assertions.assertEquals(operationPositionForUpdate,updatedOperationPosition);
  }

    @Test
    void testRemove() {
        when(idGenerator.getNextId()).thenReturn(0L);
        OperationPosition operationPosition=new OperationPosition(-1,10,product,operation);
        collectionBasedOperationPositionDao.add(operationPosition);

        long sizeAfterAdd=collectionBasedOperationPositionDao.getAll().size();
        Assertions.assertEquals(1,sizeAfterAdd);

        collectionBasedOperationPositionDao.remove(0);
        long sizeAfterRemove=collectionBasedOperationPositionDao.getAll().size();

        Assertions.assertEquals(0,sizeAfterRemove);
    }

    @Test
    void testGetAllByOwnerId() {

    }

    @Test
    void testRemoveAllByOwnerId() {

    }
}
