package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.ProcurementPositionDao;
import com.vironit.onlinepharmacy.model.Procurement;
import com.vironit.onlinepharmacy.model.ProcurementPosition;
import com.vironit.onlinepharmacy.model.ProcurementStatus;
import com.vironit.onlinepharmacy.model.Product;
import org.junit.jupiter.api.Assertions;
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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
class JpaProcurementPositionDaoTest {

    @Container
    private PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:13.0-alpine")
            .withDatabaseName("online_pharmacy")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private ProcurementPositionDao procurementPositionDao;

    private Product product;
    private Procurement procurement;
    private Procurement secondProcurement;
    private ProcurementPosition procurementPosition;
    private ProcurementPosition secondProcurementPosition;
    private ProcurementPosition thirdProcurementPosition;

    @BeforeEach
    void set() {
        product = new Product(1, "testProduct", new BigDecimal("100"), null, false);
        procurement = new Procurement(1, Instant.now(), null, ProcurementStatus.PREPARATION);
        secondProcurement = new Procurement(2, Instant.now(), null, ProcurementStatus.PREPARATION);
        procurementPosition = new ProcurementPosition();
        procurementPosition.setProduct(product);
        procurementPosition.setProcurement(procurement);
        procurementPosition.setQuantity(10);
        secondProcurementPosition = new ProcurementPosition();
        secondProcurementPosition.setProduct(product);
        secondProcurementPosition.setProcurement(secondProcurement);
        secondProcurementPosition.setQuantity(11);
        thirdProcurementPosition = new ProcurementPosition();
        thirdProcurementPosition.setProduct(product);
        thirdProcurementPosition.setProcurement(procurement);
        thirdProcurementPosition.setQuantity(14);
    }

    @Test
    void addShouldInsertPositionToCollection() {
        long id = procurementPositionDao.add(procurementPosition);

        long sizeAfterAdd = procurementPositionDao.getAll().size();

        Assertions.assertEquals(1, sizeAfterAdd);
        Assertions.assertEquals(1, id);
    }

    @Test
    void getShouldGetPositionFromCollection() {
        long id = procurementPositionDao.add(procurementPosition);

        ProcurementPosition acquiredProcurementPosition = procurementPositionDao.get(id)
                .get();

        Assertions.assertEquals(procurementPosition, acquiredProcurementPosition);
    }

    @Test
    void addAllGetAllShouldAddAndGetAllPositionsFromCollection() {
        Collection<ProcurementPosition> procurementPositions = List.of(procurementPosition, secondProcurementPosition, thirdProcurementPosition);

        procurementPositionDao.addAll(procurementPositions);

        Collection<ProcurementPosition> acquiredProcurementPositions = procurementPositionDao.getAll();
        Assertions.assertEquals(procurementPositions, acquiredProcurementPositions);
    }

    @Test
    void updateShouldUpdatePositionInCollection() {
        procurementPositionDao.add(procurementPosition);
        ProcurementPosition procurementPositionForUpdate = new ProcurementPosition(1, 15, product, procurement);

        procurementPositionDao.update(procurementPositionForUpdate);

        ProcurementPosition updatedProcurementPosition = procurementPositionDao.get(1)
                .get();

        Assertions.assertEquals(procurementPositionForUpdate, updatedProcurementPosition);
    }

    @Test
    void removeShouldRemovePositionFromCollection() {
        procurementPositionDao.add(procurementPosition);

        procurementPositionDao.remove(1);
        long sizeAfterRemove = procurementPositionDao.getAll().size();

        Assertions.assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getAllByOwnerIdShouldGetAllPositionsOfOperation() {
        procurementPositionDao.add(procurementPosition);
        procurementPositionDao.add(secondProcurementPosition);
        procurementPositionDao.add(thirdProcurementPosition);

        Collection<ProcurementPosition> actualProcurementPositions = procurementPositionDao.getAllByOwnerId(1);

        Collection<ProcurementPosition> expectedProcurementPositions = List.of(procurementPosition, thirdProcurementPosition);
        Assertions.assertEquals(expectedProcurementPositions, actualProcurementPositions);
    }

    @Test
    void removeAllByOwnerIdShouldRemoveAllPositionsOfOperation() {
        procurementPositionDao.add(procurementPosition);
        procurementPositionDao.add(secondProcurementPosition);
        procurementPositionDao.add(thirdProcurementPosition);

        procurementPositionDao.removeAllByOwnerId(1);
        Collection<ProcurementPosition> actualProcurementPositions = procurementPositionDao.getAll();

        Collection<ProcurementPosition> expectedProcurementPositions = List.of(secondProcurementPosition);
        Assertions.assertEquals(expectedProcurementPositions, actualProcurementPositions);
    }
}
