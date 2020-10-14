package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.model.Procurement;
import com.vironit.onlinepharmacy.model.ProcurementStatus;
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
class CollectionBasedProcurementDaoTest {

    private static User user;
    @Mock
    private IdGenerator idGenerator;
    @InjectMocks
    private CollectionBasedProcurementDao procurementDao;
    private Procurement procurement;

    @BeforeAll
    static void init() {
        user = new User(1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
    }

    @BeforeEach
    void set() {
        procurement = new Procurement(-1, Instant.now(), user, ProcurementStatus.PREPARATION);
    }

    @Test
    void testAdd() {
        when(idGenerator.getNextId())
                .thenReturn(0L);

        long id = procurementDao.add(procurement);

        long sizeAfterAdd = procurementDao.getAll()
                .size();
        Assertions.assertEquals(1, sizeAfterAdd);
        verify(idGenerator, times(1)).getNextId();
        Assertions.assertEquals(0, id);
    }

    @Test
    void testGet() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        long id = procurementDao.add(procurement);

        Procurement acquiredProcurement = procurementDao.get(id)
                .get();

        Assertions.assertEquals(procurement, acquiredProcurement);
    }

    @Test
    void testAddAllGetAll() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        Procurement secondProcurement = new Procurement(-1, Instant.now(), user, ProcurementStatus.PREPARATION);
        Procurement thirdProcurement = new Procurement(-1, Instant.now(), user, ProcurementStatus.PREPARATION);
        Collection<Procurement> procurements = new ArrayList<>();
        procurements.add(procurement);
        procurements.add(secondProcurement);
        procurements.add(thirdProcurement);
        procurementDao.addAll(procurements);

        Collection<Procurement> acquiredProcurements = procurementDao.getAll();

        Assertions.assertEquals(procurements, acquiredProcurements);
    }

    @Test
    void testUpdate() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        procurementDao.add(procurement);
        Procurement procurementForUpdate = new Procurement(0, Instant.now(), user, ProcurementStatus.APPROVED);

        procurementDao.update(procurementForUpdate);

        Procurement updatedProcurement = procurementDao.get(0)
                .get();

        Assertions.assertEquals(procurementForUpdate, updatedProcurement);
    }

    @Test
    void testRemove() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        procurementDao.add(procurement);

        procurementDao.remove(0);
        long sizeAfterRemove = procurementDao.getAll()
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
        Procurement secondProcurement = new Procurement(-1, Instant.now(), secondUser, ProcurementStatus.PREPARATION);
        Procurement thirdProcurement = new Procurement(-1, Instant.now(), user, ProcurementStatus.PREPARATION);

        procurementDao.add(procurement);
        procurementDao.add(secondProcurement);
        procurementDao.add(thirdProcurement);

        Collection<Procurement> actualProcurement = procurementDao.getAllByOwnerId(1);

        Collection<Procurement> expectedProcurement = new ArrayList<>();
        expectedProcurement.add(procurement);
        expectedProcurement.add(thirdProcurement);
        Assertions.assertEquals(expectedProcurement, actualProcurement);
    }

    @Test
    void testRemoveAllByOwnerId() {
        User secondUser = new User(2, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        Procurement secondProcurement = new Procurement(-1, Instant.now(), secondUser, ProcurementStatus.PREPARATION);
        Procurement thirdProcurement = new Procurement(-1, Instant.now(), user, ProcurementStatus.PREPARATION);
        procurementDao.add(procurement);
        procurementDao.add(secondProcurement);
        procurementDao.add(thirdProcurement);

        procurementDao.removeAllByOwnerId(1);

        Collection<Procurement> actualProcurement = procurementDao.getAll();
        Collection<Procurement> expectedProcurement = new ArrayList<>();
        expectedProcurement.add(secondProcurement);
        Assertions.assertEquals(expectedProcurement, actualProcurement);
    }

}
