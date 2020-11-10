package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.CreditCardDao;
import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.model.CreditCard;
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

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
class JpaCreditCardDaoTest {

    @Container
    private PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:13.0-alpine")
            .withDatabaseName("online_pharmacy")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private CreditCardDao creditCardDao;
    @Autowired
    private UserDao userDao;

    private User user;
    private User secondUser;
    private CreditCard creditCard;
    private CreditCard secondCreditCard;
    private CreditCard thirdCreditCard;

    @BeforeEach
    void before() {
        user = new User();
        user.setFirstName("testFirstName");
        user.setMiddleName("testMiddleName");
        user.setLastName("testLastName");
        user.setDateOfBirth(LocalDate.of(2000, 12, 12));
        user.setEmail("test@email.com");
        user.setPassword("testpass123");
        user.setRole(Role.CONSUMER);
        userDao.add(user);
        secondUser = new User();
        secondUser.setFirstName("testFirstName");
        secondUser.setMiddleName("testMiddleName");
        secondUser.setLastName("testLastName");
        secondUser.setDateOfBirth(LocalDate.of(2000, 10, 12));
        secondUser.setEmail("test2@email.com");
        secondUser.setPassword("testpass123");
        secondUser.setRole(Role.CONSUMER);
        userDao.add(secondUser);
        creditCard = new CreditCard();
        creditCard.setCardNumber("8274473847284729");
        creditCard.setOwnerName("TEST USER");
        creditCard.setValidThru(LocalDate.of(2000, 12, 1));
        creditCard.setCvv("123");
        creditCard.setOwner(user);
        secondCreditCard = new CreditCard();
        secondCreditCard.setCardNumber("8574574457534729");
        secondCreditCard.setOwnerName("TEST USER");
        secondCreditCard.setValidThru(LocalDate.of(2000, 12, 1));
        secondCreditCard.setCvv("241");
        secondCreditCard.setOwner(secondUser);
        thirdCreditCard = new CreditCard();
        thirdCreditCard.setCardNumber("8274093922949375");
        thirdCreditCard.setOwnerName("INSTANT ISSUE");
        thirdCreditCard.setValidThru(LocalDate.of(2000, 12, 1));
        thirdCreditCard.setCvv("124");
        thirdCreditCard.setOwner(user);
    }

    @Test
    void addShouldAddCreditCardToDatabase() {
        long id = creditCardDao.add(creditCard);

        assertEquals(1, id);
    }

    @Test
    void getShouldReturnCreditCardFromDatabase() {
        creditCardDao.add(creditCard);
        CreditCard acquiredCreditCard = creditCardDao.get(1)
                .get();

        assertEquals(creditCard.getCardNumber(), acquiredCreditCard.getCardNumber());
    }

    @Test
    void addAllShouldAddAllCreditCardsToDatabase() {
        Collection<CreditCard> creditCards = List.of(creditCard,secondCreditCard, thirdCreditCard);

        boolean addAllResult = creditCardDao.addAll(creditCards);

        assertTrue(addAllResult);
    }

    @Test
    void getAllShouldGetAllCreditCardsFromDatabase() {
        creditCardDao.add(creditCard);

        Collection<CreditCard> creditCards = creditCardDao.getAll();

        int acquiredSize = creditCards.size();
        assertEquals(1, acquiredSize);
    }

    @Test
    void removeShouldRemoveCreditCardFromDatabase() {
        creditCardDao.add(creditCard);

        boolean successfulRemove = creditCardDao.remove(1);

        assertTrue(successfulRemove);
    }

    @Test
    void getAllByOwnerIdShouldReturnAllCreditCardsOfUser() {
        creditCardDao.add(creditCard);
        creditCardDao.add(secondCreditCard);
        creditCardDao.add(thirdCreditCard);

        Collection<CreditCard> actualCreditCards = creditCardDao.getAllByOwnerId(1);

        int actualSize = actualCreditCards.size();
        assertEquals(2, actualSize);
    }

    @Test
    void removeAllByOwnerIdShouldRemoveAllCreditCardsOfUser() {
        creditCardDao.add(creditCard);
        creditCardDao.add(secondCreditCard);
        creditCardDao.add(thirdCreditCard);

        creditCardDao.removeAllByOwnerId(1);

        Collection<CreditCard> actualCreditCards = creditCardDao.getAll();
        int actualSize = actualCreditCards.size();

        assertEquals(1, actualSize);
    }
}
