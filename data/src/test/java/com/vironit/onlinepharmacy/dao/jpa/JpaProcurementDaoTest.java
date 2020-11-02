package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.ProcurementDao;
import com.vironit.onlinepharmacy.model.Procurement;
import com.vironit.onlinepharmacy.model.ProcurementStatus;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
class JpaProcurementDaoTest {

    @Container
    private PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:13.0-alpine")
            .withDatabaseName("online_pharmacy")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private ProcurementDao procurementDao;

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
        procurement.setDate(Instant.now());
        procurement.setOwner(user);
        procurement.setProcurementStatus(ProcurementStatus.PREPARATION);
        secondProcurement = new Procurement();
        secondProcurement.setDate(Instant.now());
        secondProcurement.setOwner(secondUser);
        secondProcurement.setProcurementStatus(ProcurementStatus.PREPARATION);
        thirdProcurement = new Procurement();
        thirdProcurement.setDate(Instant.now());
        thirdProcurement.setOwner(user);
        thirdProcurement.setProcurementStatus(ProcurementStatus.PREPARATION);
    }

    @Test
    void addShouldAddProcurementToCollection() {
        long id = procurementDao.add(procurement);

        long sizeAfterAdd = procurementDao.getAll()
                .size();
        assertEquals(1, sizeAfterAdd);

        assertEquals(1, id);
    }

    @Test
    void getShouldReturnProcurementFromCollection() {
        long id = procurementDao.add(procurement);

        Procurement acquiredProcurement = procurementDao.get(id)
                .get();

        assertEquals(procurement, acquiredProcurement);
    }

    @Test
    void addAllGetAllShouldAddAndGetAllProcurementsFromCollection() {
        Collection<Procurement> procurements = List.of(procurement,secondProcurement,thirdProcurement);

        procurementDao.addAll(procurements);

        Collection<Procurement> acquiredProcurements = procurementDao.getAll();

        assertEquals(procurements, acquiredProcurements);
    }

    @Test
    void updateShouldUpdateProcurementInCollection() {
        procurementDao.add(procurement);
        Procurement procurementForUpdate = new Procurement(1, Instant.now(), user, ProcurementStatus.APPROVED);

        procurementDao.update(procurementForUpdate);

        Procurement updatedProcurement = procurementDao.get(1)
                .get();
        assertEquals(procurementForUpdate, updatedProcurement);
    }

    @Test
    void removeShouldRemoveProcurementFromCollection() {
        procurementDao.add(procurement);

        procurementDao.remove(1);
        long sizeAfterRemove = procurementDao.getAll()
                .size();

        assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getAllByOwnerIdShouldReturnAllProcurementsOfUser() {
        procurementDao.add(procurement);
        procurementDao.add(secondProcurement);
        procurementDao.add(thirdProcurement);

        Collection<Procurement> actualProcurement = procurementDao.getAllByOwnerId(1);

        Collection<Procurement> expectedProcurement = new ArrayList<>();
        expectedProcurement.add(procurement);
        expectedProcurement.add(thirdProcurement);
        assertEquals(expectedProcurement, actualProcurement);
    }

    @Test
    void removeAllByOwnerIdShouldRemoveAllProcurementsOfUser() {
        procurementDao.add(procurement);
        procurementDao.add(secondProcurement);
        procurementDao.add(thirdProcurement);

        procurementDao.removeAllByOwnerId(1);

        Collection<Procurement> actualProcurement = procurementDao.getAll();
        Collection<Procurement> expectedProcurement = new ArrayList<>();
        expectedProcurement.add(secondProcurement);
        assertEquals(expectedProcurement, actualProcurement);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualZero() {
        long totalElements = procurementDao.getTotalElements();

        assertEquals(0, totalElements);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualTwo() {
        procurementDao.add(procurement);
        procurementDao.add(secondProcurement);

        long totalElements = procurementDao.getTotalElements();

        assertEquals(2, totalElements);
    }

    @Test
    void getPageShouldReturnASecondPageWithOneElement() {
        procurementDao.add(procurement);
        procurementDao.add(secondProcurement);
        procurementDao.add(thirdProcurement);

        Collection<Procurement> procurementPage = procurementDao.getPage(2, 2);

        Collection<Procurement> expectedProcurementPage = new ArrayList<>();
        expectedProcurementPage.add(thirdProcurement);
        assertEquals(expectedProcurementPage, procurementPage);
    }

    @Test
    void getPageShouldReturnAThirdPageWithNoElements() {
        procurementDao.add(procurement);
        procurementDao.add(secondProcurement);
        procurementDao.add(thirdProcurement);

        Collection<Procurement> procurementPage = procurementDao.getPage(3, 2);

        Collection<Procurement> expectedProcurementPage = new ArrayList<>();
        assertEquals(expectedProcurementPage, procurementPage);
    }
}
