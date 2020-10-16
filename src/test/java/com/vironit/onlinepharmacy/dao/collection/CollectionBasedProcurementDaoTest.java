package com.vironit.onlinepharmacy.dao.collection;

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
class CollectionBasedProcurementDaoTest {

    @Mock
    private IdGenerator idGenerator;
    @InjectMocks
    private CollectionBasedProcurementDao procurementDao;

    private User user;
    private User secondUser;
    private Procurement procurement;
    private Procurement secondProcurement;
    private Procurement thirdProcurement;

    @BeforeEach
    void set() {
        user = new User(1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.PROCUREMENT_SPECIALIST);
        secondUser = new User(2, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.PROCUREMENT_SPECIALIST);
        procurement = new Procurement(-1, Instant.now(), user, ProcurementStatus.PREPARATION);
        secondProcurement = new Procurement(-1, Instant.now(), secondUser, ProcurementStatus.PREPARATION);
        thirdProcurement = new Procurement(-1, Instant.now(), user, ProcurementStatus.PREPARATION);
    }

    @Test
    void addShouldAddProcurementToCollection() {
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
    void getShouldReturnProcurementFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        long id = procurementDao.add(procurement);

        Procurement acquiredProcurement = procurementDao.get(id)
                .get();

        Assertions.assertEquals(procurement, acquiredProcurement);
    }

    @Test
    void addAllGetAllShouldAddAndGetAllProcurementsFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        Collection<Procurement> procurements = new ArrayList<>();
        procurements.add(procurement);
        procurements.add(secondProcurement);
        procurements.add(thirdProcurement);
        procurementDao.addAll(procurements);

        Collection<Procurement> acquiredProcurements = procurementDao.getAll();

        Assertions.assertEquals(procurements, acquiredProcurements);
    }

    @Test
    void updateShouldUpdateProcurementInCollection() {
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
    void removeShouldRemoveProcurementFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        procurementDao.add(procurement);

        procurementDao.remove(0);
        long sizeAfterRemove = procurementDao.getAll()
                .size();

        Assertions.assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getAllByOwnerIdShouldReturnAllProcurementsOfUser() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
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
    void removeAllByOwnerIdShouldRemoveAllProcurementsOfUser() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        procurementDao.add(procurement);
        procurementDao.add(secondProcurement);
        procurementDao.add(thirdProcurement);

        procurementDao.removeAllByOwnerId(1);

        Collection<Procurement> actualProcurement = procurementDao.getAll();
        Collection<Procurement> expectedProcurement = new ArrayList<>();
        expectedProcurement.add(secondProcurement);
        Assertions.assertEquals(expectedProcurement, actualProcurement);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualZero() {
        int totalElements = procurementDao.getTotalElements();
        Assertions.assertEquals(0, totalElements);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualTwo() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L);
        procurementDao.add(procurement);
        procurementDao.add(secondProcurement);
        int totalElements = procurementDao.getTotalElements();
        Assertions.assertEquals(2, totalElements);
    }

    @Test
    void getPageShouldReturnASecondPageWithOneElement() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        procurementDao.add(procurement);
        procurementDao.add(secondProcurement);
        procurementDao.add(thirdProcurement);

        Collection<Procurement> procurementPage = procurementDao.getPage(2, 2);

        Collection<Procurement> expectedProcurementPage = new ArrayList<>();
        expectedProcurementPage.add(thirdProcurement);
        Assertions.assertEquals(expectedProcurementPage, procurementPage);
    }

    @Test
    void getPageShouldReturnAThirdPageWithNoElements() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        procurementDao.add(procurement);
        procurementDao.add(secondProcurement);
        procurementDao.add(thirdProcurement);

        Collection<Procurement> procurementPage = procurementDao.getPage(3, 2);

        Collection<Procurement> expectedProcurementPage = new ArrayList<>();
        Assertions.assertEquals(expectedProcurementPage, procurementPage);
    }
}
