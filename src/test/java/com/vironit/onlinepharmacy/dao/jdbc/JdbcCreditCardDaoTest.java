package com.vironit.onlinepharmacy.dao.jdbc;

import com.vironit.onlinepharmacy.model.CreditCard;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
class JdbcCreditCardDaoTest {

@Container
    private PostgreSQLContainer<?> postgreSqlContainer=new PostgreSQLContainer<>()
            .withDatabaseName("online_pharmacy")
            .withUsername("test")
            .withPassword("test")
     .withInitScript("schema.sql");

    private DataSource dataSource;

    private JdbcCreditCardDao creditCardDao;
    private User user;
    private User secondUser;
    private CreditCard creditCard;
    private CreditCard secondCreditCard;
    private CreditCard thirdCreditCard;

    @BeforeEach
    void before() throws SQLException {
        dataSource = getDataSource(postgreSqlContainer);
        creditCardDao =new JdbcCreditCardDao(dataSource);
        user = new User(1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.of(2000,12,12), "test@email.com", "testpass123", Role.CONSUMER);
        secondUser = new User(2, "testFirstName2", "testMiddleName2", "testLastName2",
                LocalDate.of(2000,11,12), "test@email.com", "testpass123", Role.CONSUMER);
        creditCard = new CreditCard(1,"8274473847284729","TEST USER",LocalDate.of(2000,12,1),123,user);
        secondCreditCard = new CreditCard(2,"8574574457534729","TEST USER",LocalDate.of(2000,12,1),241,secondUser);
        thirdCreditCard = new CreditCard(3,"8274093922949375","INSTANT ISSUE",LocalDate.of(2000,12,1),124,user);
        String sql="INSERT INTO roles (name) " +
                "VALUES('"+Role.CONSUMER.toString()+"');\n" +
                "INSERT INTO users (first_name, middle_name, last_name, date_of_birth, email, password, role_id) " +
                "VALUES('testFirstName','testMiddleName','testLastName','2000-12-12','test@email.com','testpass123',1),"+
                "('testFirstName2','testMiddleName2','testLastName2','2000-11-12','test@email.com','testpass123',1);\n"+
                "INSERT INTO credit_cards (card_number, owner_name, valid_thru, cvv, user_id) " +
                "VALUES('8274473847284729','TEST USER','2000-12-12',123,1);";
        executeUpdate(dataSource,sql);
    }


    @Test
    void addShouldAddCreditCardToDatabase() {
        long id = creditCardDao.add(secondCreditCard);

        assertEquals(2, id);
    }

    @Test
    void getShouldReturnCreditCardFromDatabase() {
        CreditCard acquiredCreditCard = creditCardDao.get(1)
                .get();

        assertEquals(creditCard.getCardNumber(), acquiredCreditCard.getCardNumber());
    }

    @Test
    void addAllShouldAddAllCreditCardsToDatabase() {
        Collection<CreditCard> creditCards = List.of(secondCreditCard,thirdCreditCard);

        boolean addAllResult=creditCardDao.addAll(creditCards);

        assertTrue(addAllResult);
    }

    @Test
    void getAllShouldGetAllCreditCardsFromDatabase() {
        Collection<CreditCard> creditCards = creditCardDao.getAll();
        int acquiredSize=creditCards.size();

        assertEquals(1,acquiredSize);
    }

    @Test
    void removeShouldRemoveCreditCardFromDatabase() {
       boolean successfulRemove= creditCardDao.remove(1);

        assertTrue(successfulRemove);
    }

    @Test
    void getAllByOwnerIdShouldReturnAllCreditCardsOfUser() {
        creditCardDao.add(secondCreditCard);
        creditCardDao.add(thirdCreditCard);

        Collection<CreditCard> actualCreditCards = creditCardDao.getAllByOwnerId(1);

        int actualSize=actualCreditCards.size();
        assertEquals(2,actualSize);
    }

    @Test
    void removeAllByOwnerIdShouldRemoveAllCreditCardsOfUser() {
        creditCardDao.add(secondCreditCard);
        creditCardDao.add(thirdCreditCard);

        creditCardDao.removeAllByOwnerId(1);

        Collection<CreditCard> actualCreditCards = creditCardDao.getAll();
        int actualSize=actualCreditCards.size();

        assertEquals(1, actualSize);
    }


private int executeUpdate(DataSource dataSource, String sql) throws SQLException {
    try(Statement statement = dataSource.getConnection()
            .createStatement()) {
       return statement.executeUpdate(sql);
    }
}

    private DataSource getDataSource(JdbcDatabaseContainer<?> container) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(container.getJdbcUrl());
        hikariConfig.setUsername(container.getUsername());
        hikariConfig.setPassword(container.getPassword());
        hikariConfig.setDriverClassName(container.getDriverClassName());
        return new HikariDataSource(hikariConfig);
    }

}
