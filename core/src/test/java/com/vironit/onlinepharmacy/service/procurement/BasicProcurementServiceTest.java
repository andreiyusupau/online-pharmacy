package com.vironit.onlinepharmacy.service.procurement;

import com.vironit.onlinepharmacy.dao.ProcurementDao;
import com.vironit.onlinepharmacy.dao.ProcurementPositionDao;
import com.vironit.onlinepharmacy.dto.PositionDto;
import com.vironit.onlinepharmacy.dto.ProcurementDto;
import com.vironit.onlinepharmacy.model.*;
import com.vironit.onlinepharmacy.service.exception.ProcurementServiceException;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.service.stock.StockService;
import com.vironit.onlinepharmacy.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@Disabled
@ExtendWith(MockitoExtension.class)
public class BasicProcurementServiceTest {

    @Mock
    private ProcurementDao procurementDao;
    @Mock
    private ProcurementPositionDao procurementPositionDao;
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
    private ProcurementPosition firstProcurementPosition;
    private ProcurementPosition secondProcurementPosition;
    private ProcurementPosition thirdProcurementPosition;
    private PositionDto firstOperationPositionDto;
    private PositionDto secondOperationPositionDto;
    private PositionDto thirdOperationPositionDto;
    private List<PositionDto> operationPositionDtoList;
    private Procurement secondProcurement;
    private Procurement thirdProcurement;
    private Collection<Procurement> procurements;


    @BeforeEach
    void set() {
        user = new User(1, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123", Role.CONSUMER);
        firstProduct = new Product(1, "firstProduct", new BigDecimal("35"), null, false);
        secondProduct = new Product(2, "secondProduct", new BigDecimal("345"), null, false);
        thirdProduct = new Product(3, "thirdProduct", new BigDecimal("67"), null, false);
        procurement = new Procurement(1, Instant.now(), user, ProcurementStatus.PREPARATION);
        firstProcurementPosition = new ProcurementPosition(1, 7, firstProduct, procurement);
        secondProcurementPosition = new ProcurementPosition(2, 64, secondProduct, procurement);
        thirdProcurementPosition = new ProcurementPosition(3, 124, thirdProduct, procurement);
        firstOperationPositionDto = new PositionDto( 1, 10);
        secondOperationPositionDto = new PositionDto( 2, 15);
        thirdOperationPositionDto = new PositionDto( 3, 25);
        operationPositionDtoList = List.of(firstOperationPositionDto, secondOperationPositionDto, thirdOperationPositionDto);
        secondProcurement = new Procurement(2, Instant.now(), user, ProcurementStatus.APPROVED);
        thirdProcurement = new Procurement(3, Instant.now(), user, ProcurementStatus.PREPARATION);
        procurements = List.of(procurement, secondProcurement, thirdProcurement);
    }

    @Test
    void addShouldUseDao() {
        when(userService.get(anyLong()))
                .thenReturn(user);

        ProcurementDto procurementDto = new ProcurementDto(0, 1, operationPositionDtoList);

        long id = procurementService.add(procurementDto);

        Assertions.assertEquals(0, id);
    }

    @Test
    void approveProcurementShouldNotThrowException() {
        when(procurementDao.get(anyLong()))
                .thenReturn(Optional.of(procurement));

        procurementService.approveProcurement(1);
    }

    @Test
    void approveProcurementShouldThrowException() {
        when(procurementDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ProcurementServiceException.class,
                () -> procurementService.approveProcurement(1));

        String expectedMessage = "Can't approve procurement. Procurement with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void completeProcurementShouldNotThrowException() {
        when(procurementDao.get(anyLong()))
                .thenReturn(Optional.of(procurement));

        procurementService.completeProcurement(1);
    }

    @Test
    void completeProcurementShouldThrowException() {
        when(procurementDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ProcurementServiceException.class, () -> procurementService.completeProcurement(1));

        String expectedMessage = "Can't complete procurement. Procurement with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void cancelProcurementShouldNotThrowException() {
        when(procurementDao.get(anyLong())).thenReturn(Optional.of(procurement));

        procurementService.cancelProcurement(1);
    }

    @Test
    void cancelProcurementShouldThrowException() {
        when(procurementDao.get(anyLong())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ProcurementServiceException.class, () -> procurementService.cancelProcurement(1));

        String expectedMessage = "Can't cancel procurement. Procurement with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getShouldNotThrowException() {
        when(procurementDao.get(anyLong()))
                .thenReturn(Optional.of(procurement));
        Procurement actualProcurement = procurementDao.get(1)
                .get();
        Assertions.assertEquals(procurement, actualProcurement);
    }

    @Test
    void getShouldThrowException() {
        when(procurementDao.get(anyLong())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ProcurementServiceException.class, () -> procurementService.get(1));

        String expectedMessage = "Can't get procurement. Procurement with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getAllShouldNotThrowException() {
        when(procurementDao.getAll())
                .thenReturn(procurements);

        Collection<Procurement> actualProcurements = procurementService.getAll();

        Assertions.assertEquals(procurements, actualProcurements);
    }

    @Test
    void updateShouldUseDao() {
        ProcurementDto procurementUpdateData = new ProcurementDto(1, 1, operationPositionDtoList);
        when(procurementDao.get(1))
                .thenReturn(Optional.of(procurement));

        procurementService.update(procurementUpdateData);

        verify(procurementPositionDao).removeAllByOwnerId(1);
        verify(procurementPositionDao).addAll(any());
    }

    @Test
    void removeShouldUseDao() {
        when(procurementDao.remove(anyLong()))
                .thenReturn(true);
        when(procurementPositionDao.removeAllByOwnerId(anyLong()))
                .thenReturn(true);

        procurementService.remove(1);

        verify(procurementPositionDao).removeAllByOwnerId(1);
        verify(procurementDao).remove(1);
    }
}
