package com.vironit.onlinepharmacy.dao.jdbc;

import com.vironit.onlinepharmacy.model.CreditCard;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;


class JdbcCreditCardDaoTest {

    private static final PostgreSQLContainer postgreSqlContainer=new PostgreSQLContainer("postgresql:latest")
            .withDatabaseName("online_pharmacy")
            .withUsername("test")
            .withPassword("test");


    private DataSource dataSource;
    private JdbcCreditCardDao creditCardDao;

    private User user;
    private User secondUser;
    private CreditCard creditCard;
    private CreditCard secondCreditCard;
    private CreditCard thirdCreditCard;

    @BeforeEach
    void set() {
      //  dataSource=getDataSource();
        creditCardDao=new JdbcCreditCardDao(dataSource);
        user = new User(1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
        secondUser = new User(2, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
        creditCard = new CreditCard(-1,"8274473847284729","TEST USER",LocalDate.of(2000,12,1),123,user);
        secondCreditCard = new CreditCard(-1,"8574574457534729","TEST USER",LocalDate.of(2000,12,1),241,secondUser);
        thirdCreditCard = new CreditCard(-1,"8274093922949375","INSTANT ISSUE",LocalDate.of(2000,12,1),124,user);
    }

    @Test
    void addShouldAddCreditCardToCollection() {

        long id = creditCardDao.add(creditCard);

        long sizeAfterAdd = creditCardDao.getAll()
                .size();
        Assertions.assertEquals(1, sizeAfterAdd);
        Assertions.assertEquals(0, id);
    }

    @Test
    void getShouldReturnCreditCardFromCollection() {
        long id = creditCardDao.add(creditCard);

        CreditCard acquiredCreditCard = creditCardDao.get(id)
                .get();

        Assertions.assertEquals(creditCard, acquiredCreditCard);
    }

    @Test
    void addAllGetAllShouldAddAndGetAllCreditCardsFromCollection() {

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

        creditCardDao.add(creditCard);

        creditCardDao.remove(0);
        long sizeAfterRemove = creditCardDao.getAll()
                .size();

        Assertions.assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getAllByOwnerIdShouldReturnAllCreditCardsOfUser() {

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
