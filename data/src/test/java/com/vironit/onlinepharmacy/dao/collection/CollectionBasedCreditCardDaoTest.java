package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.collection.util.IdGenerator;
import com.vironit.onlinepharmacy.model.CreditCard;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
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

import static org.mockito.Mockito.*;

@Disabled
@ExtendWith(MockitoExtension.class)
class CollectionBasedCreditCardDaoTest {

    @Mock
    private IdGenerator idGenerator;
    @InjectMocks
    private CollectionBasedCreditCardDao creditCardDao;

    private User user;
    private User secondUser;
    private CreditCard creditCard;
    private CreditCard secondCreditCard;
    private CreditCard thirdCreditCard;

    @BeforeEach
    void set() {
        user = new User(1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
        secondUser = new User(2, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
        creditCard = new CreditCard(-1, "8274473847284729", "TEST USER", LocalDate.of(2000, 12, 1), "123", user);
        secondCreditCard = new CreditCard(-1, "8574574457534729", "TEST USER", LocalDate.of(2000, 12, 1), "241", secondUser);
        thirdCreditCard = new CreditCard(-1, "8274093922949375", "INSTANT ISSUE", LocalDate.of(2000, 12, 1), "124", user);
    }

    @Test
    void addShouldAddCreditCardToCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);

        long id = creditCardDao.add(creditCard);

        long sizeAfterAdd = creditCardDao.getAll()
                .size();
        Assertions.assertEquals(1, sizeAfterAdd);
        verify(idGenerator, times(1)).getNextId();
        Assertions.assertEquals(0, id);
    }

    @Test
    void getShouldReturnCreditCardFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        long id = creditCardDao.add(creditCard);

        CreditCard acquiredCreditCard = creditCardDao.get(id)
                .get();

        Assertions.assertEquals(creditCard, acquiredCreditCard);
    }

    @Test
    void addAllGetAllShouldAddAndGetAllCreditCardsFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        Collection<CreditCard> creditCards = new ArrayList<>();
        creditCards.add(creditCard);
        creditCards.add(secondCreditCard);
        creditCards.add(thirdCreditCard);
        creditCardDao.addAll(creditCards);

        Collection<CreditCard> acquiredCreditCards = creditCardDao.getAll();

        Assertions.assertEquals(creditCards, acquiredCreditCards);
    }

    @Test
    void removeShouldRemoveCreditCardFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        creditCardDao.add(creditCard);

        creditCardDao.remove(0);
        long sizeAfterRemove = creditCardDao.getAll()
                .size();

        Assertions.assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getAllByOwnerIdShouldReturnAllCreditCardsOfUser() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        creditCardDao.add(creditCard);
        creditCardDao.add(secondCreditCard);
        creditCardDao.add(thirdCreditCard);

        Collection<CreditCard> actualCreditCards = creditCardDao.getAllByOwnerId(1);

        Collection<CreditCard> expectedCreditCards = new ArrayList<>();
        expectedCreditCards.add(creditCard);
        expectedCreditCards.add(thirdCreditCard);
        Assertions.assertEquals(expectedCreditCards, actualCreditCards);
    }

    @Test
    void removeAllByOwnerIdShouldRemoveAllCreditCardsOfUser() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        creditCardDao.add(creditCard);
        creditCardDao.add(secondCreditCard);
        creditCardDao.add(thirdCreditCard);

        creditCardDao.removeAllByOwnerId(1);

        Collection<CreditCard> actualCreditCards = creditCardDao.getAll();
        Collection<CreditCard> expectedCreditCards = new ArrayList<>();
        expectedCreditCards.add(secondCreditCard);
        Assertions.assertEquals(expectedCreditCards, actualCreditCards);
    }
}
