package com.vironit.onlinepharmacy.service.procurement;

import com.vironit.onlinepharmacy.dao.OperationPositionDao;
import com.vironit.onlinepharmacy.dao.ProcurementDao;
import com.vironit.onlinepharmacy.dto.OperationPositionData;
import com.vironit.onlinepharmacy.dto.ProcurementCreateData;
import com.vironit.onlinepharmacy.dto.ProcurementUpdateData;
import com.vironit.onlinepharmacy.model.*;
import com.vironit.onlinepharmacy.service.procurement.exception.ProcurementException;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.service.stock.StockService;
import com.vironit.onlinepharmacy.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BasicProcurementServiceTest {

    @Mock
    private ProcurementDao procurementDao;
    @Mock
    private OperationPositionDao operationPositionDao;
    @Mock
    private StockService stockService;
    @Mock
    private UserService userService;
    @Mock
    private ProductService productService;
    @InjectMocks
    private BasicProcurementService procurementService;

    private User user;
    private Product firstProduct;
    private Product secondProduct;
    private Product thirdProduct;
    private Procurement procurement;
    private OperationPosition firstOperationPosition;
    private OperationPosition secondOperationPosition;
    private OperationPosition thirdOperationPosition;
    private OperationPositionData firstOperationPositionData;
    private OperationPositionData secondOperationPositionData;
    private OperationPositionData thirdOperationPositionData;
    private List<OperationPositionData> operationPositionDataList;
    private Procurement secondProcurement;
    private Procurement thirdProcurement;
    private Collection<Procurement> procurements;


    @BeforeEach
    void set() {
        user = new User(1, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123", Role.CONSUMER);
        firstProduct = new Product(1, "firstProduct", new BigDecimal("35"), null);
        secondProduct = new Product(2, "secondProduct", new BigDecimal("345"), null);
        thirdProduct = new Product(3, "thirdProduct", new BigDecimal("67"), null);
        procurement = new Procurement(1, Instant.now(), user, ProcurementStatus.PREPARATION);
        firstOperationPosition = new OperationPosition(1, 7, firstProduct, procurement);
        secondOperationPosition = new OperationPosition(2, 64, secondProduct, procurement);
        thirdOperationPosition = new OperationPosition(3, 124, thirdProduct, procurement);
        firstOperationPositionData = new OperationPositionData(1, 10);
        secondOperationPositionData = new OperationPositionData(2, 15);
        thirdOperationPositionData = new OperationPositionData(3, 25);
        operationPositionDataList = new ArrayList<>();
        operationPositionDataList.add(firstOperationPositionData);
        operationPositionDataList.add(secondOperationPositionData);
        operationPositionDataList.add(thirdOperationPositionData);
        secondProcurement = new Procurement(2, Instant.now(), user, ProcurementStatus.APPROVED);
        thirdProcurement = new Procurement(3, Instant.now(), user, ProcurementStatus.PREPARATION);
        procurements = new ArrayList<>();
        procurements.add(procurement);
        procurements.add(secondProcurement);
        procurements.add(thirdProcurement);
    }

    @Test
    void testAdd() {
        when(userService.get(anyLong()))
                .thenReturn(user);

        ProcurementCreateData procurementCreateData = new ProcurementCreateData(1, operationPositionDataList);

        long id = procurementService.add(procurementCreateData);

        Assertions.assertEquals(0, id);
    }

    @Test
    void testApproveProcurementShouldNotThrowException() {
        when(procurementDao.get(anyLong()))
                .thenReturn(Optional.of(procurement));

        procurementService.approveProcurement(1);
    }

    @Test
    void testApproveProcurementShouldThrowException() {
        when(procurementDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ProcurementException.class,
                () -> procurementService.approveProcurement(1));

        String expectedMessage = "Can't approve procurement. Procurement with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void testCompleteProcurementShouldNotThrowException() {
        when(procurementDao.get(anyLong()))
                .thenReturn(Optional.of(procurement));

        procurementService.completeProcurement(1);
    }

    @Test
    void testCompleteProcurementShouldThrowException() {
        when(procurementDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ProcurementException.class, () -> procurementService.completeProcurement(1));

        String expectedMessage = "Can't complete procurement. Procurement with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCancelProcurementShouldNotThrowException() {
        when(procurementDao.get(anyLong())).thenReturn(Optional.of(procurement));

        procurementService.cancelProcurement(1);
    }

    @Test
    void testCancelProcurementShouldThrowException() {
        when(procurementDao.get(anyLong())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ProcurementException.class, () -> procurementService.cancelProcurement(1));

        String expectedMessage = "Can't cancel procurement. Procurement with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetShouldNotThrowException() {
        when(procurementDao.get(anyLong()))
                .thenReturn(Optional.of(procurement));
        Procurement actualProcurement = procurementDao.get(1)
                .get();
        Assertions.assertEquals(procurement, actualProcurement);
    }

    @Test
    void testGetShouldThrowException() {
        when(procurementDao.get(anyLong())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ProcurementException.class, () -> procurementService.get(1));

        String expectedMessage = "Can't get procurement. Procurement with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetAllShouldNotThrowException() {
        when(procurementDao.getAll())
                .thenReturn(procurements);

        Collection<Procurement> actualProcurements = procurementService.getAll();

        Assertions.assertEquals(procurements, actualProcurements);
    }

    @Test
    void testUpdate() {
        ProcurementUpdateData procurementUpdateData = new ProcurementUpdateData(1, 1, operationPositionDataList);
        when(procurementDao.get(1))
                .thenReturn(Optional.of(procurement));

        procurementService.update(procurementUpdateData);

        verify(operationPositionDao).removeAllByOwnerId(1);
        verify(operationPositionDao).addAll(any());
    }

    @Test
    void testRemove() {
        when(procurementDao.remove(anyLong()))
                .thenReturn(true);
        when(operationPositionDao.removeAllByOwnerId(anyLong()))
                .thenReturn(true);

        procurementService.remove(1);

        verify(operationPositionDao).removeAllByOwnerId(1);
        verify(procurementDao).remove(1);
    }
}
