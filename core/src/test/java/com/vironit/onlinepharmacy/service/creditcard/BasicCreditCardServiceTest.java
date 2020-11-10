package com.vironit.onlinepharmacy.service.creditcard;

import com.vironit.onlinepharmacy.dao.CreditCardDao;
import com.vironit.onlinepharmacy.dto.CreditCardDto;
import com.vironit.onlinepharmacy.model.CreditCard;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.exception.CreditCardServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@Disabled
@ExtendWith(MockitoExtension.class)
public class BasicCreditCardServiceTest {

    @Mock
    private CreditCardDao creditCardDao;
    @InjectMocks
    private BasicCreditCardService creditCardService;

    private User user;
    private CreditCardDto creditCardDto;
    private CreditCard creditCard;
    private CreditCard secondCreditCard;
    private Collection<CreditCard> creditCards;

    @BeforeEach
    void set() {
        user = new User(0, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123", Role.CONSUMER);
        creditCardDto =new CreditCardDto("0000000000000000", "INSTANT ISSUE", LocalDate.now(), "938", 0);
        creditCard = new CreditCard(1, "0000000000000000", "INSTANT ISSUE", LocalDate.now(), "938", user);
        secondCreditCard = new CreditCard(2, "0000000000000001", "INSTANT ISSUE", LocalDate.now(), "542", user);
        creditCards = new ArrayList<>();
        creditCards.add(creditCard);
        creditCards.add(secondCreditCard);
    }

    @Test
    void addShouldUseDao() {
        when(creditCardDao.add(any()))
                .thenReturn(0L);

        long id = creditCardService.add(creditCardDto);

        verify(creditCardDao).add(creditCard);
        Assertions.assertEquals(0, id);
    }

    @Test
    void getShouldNotThrowException() {
        when(creditCardDao.get(anyLong()))
                .thenReturn(Optional.of(creditCard));

        CreditCard actualCreditCard = creditCardService.get(1);

        verify(creditCardDao).get(1);
        Assertions.assertEquals(creditCard, actualCreditCard);
    }

    @Test
    void getShouldThrowException() {
        when(creditCardDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(CreditCardServiceException.class, () -> creditCardService.get(1));

        verify(creditCardDao).get(1);
        String expectedMessage = "Can't get credit card. Credit card with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getAllShouldNotThrowException() {
        when(creditCardDao.getAll())
                .thenReturn(creditCards);

        Collection<CreditCard> actualCreditCard = creditCardService.getAll();

        Assertions.assertEquals(creditCards, actualCreditCard);
    }

    @Test
    void removeShouldUseDao() {
        when(creditCardDao.remove(anyLong()))
                .thenReturn(true);

        creditCardService.remove(1);

        verify(creditCardDao).remove(1);
    }
}
