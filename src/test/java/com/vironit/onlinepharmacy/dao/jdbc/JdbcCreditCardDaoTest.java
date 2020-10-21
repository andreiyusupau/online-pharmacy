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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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
//
    private User user;
    private User secondUser;
    private CreditCard creditCard;
    private CreditCard secondCreditCard;
    private CreditCard thirdCreditCard;

    @BeforeEach
    void before() {
        dataSource = getDataSource(postgreSqlContainer);
        creditCardDao =new JdbcCreditCardDao(dataSource);
        user = new User(1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
        secondUser = new User(2, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
        creditCard = new CreditCard(-1,"8274473847284729","TEST USER",LocalDate.of(2000,12,1),123,user);
        secondCreditCard = new CreditCard(-1,"8574574457534729","TEST USER",LocalDate.of(2000,12,1),241,secondUser);
        thirdCreditCard = new CreditCard(-1,"8274093922949375","INSTANT ISSUE",LocalDate.of(2000,12,1),124,user);
    }


    @Test
    void test() throws SQLException {
        System.out.println(postgreSqlContainer.isRunning());

//        String sql="CREATE TABLE credit_cards\n" +
//                "(\n" +
//                "    id          bigint                NOT NULL,\n" +
//                "    card_number character varying(19) NOT NULL,\n" +
//                "    owner_name  character varying(50) NOT NULL,\n" +
//                "    valid_thru  date                  NOT NULL,\n" +
//                "    cvv         integer               NOT NULL,\n" +
//                "    user_id     bigint                NOT NULL\n" +
//                ");";
//        try( Statement statement = dataSource.getConnection()
//                .createStatement()) {
//            statement.executeUpdate(sql);
//        }
        String sql2="INSERT INTO credit_cards (id, card_number, owner_name, valid_thru, cvv, user_id) " +
                "VALUES(12,'2512','jack','2000-12-12',123,12);";
        try( Statement statement = dataSource.getConnection()
                .createStatement()) {
          statement.executeUpdate(sql2);
        }
        String sql3="SELECT card_number FROM credit_cards";
        try(Statement statement = dataSource.getConnection()
                .createStatement()) {
            try(ResultSet resultSet=statement.executeQuery(sql3);) {
               resultSet.next();
                System.out.println(resultSet.getString(1));
            }
        }
    }

//    @Test
//    void addShouldAddCreditCardToCollection(){
//
//        long id = creditCardDao.add(creditCard);
//
//        long sizeAfterAdd = creditCardDao.getAll()
//                .size();
//        Assertions.assertEquals(1, sizeAfterAdd);
//        Assertions.assertEquals(0, id);
//    }

//    @Test
//    public void testSimple() throws SQLException {
//        try (PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgresql:latest")) {
//            postgres.start();
//
//          //  ResultSet resultSet = performQuery(postgres, "SELECT 1");
//          //  int resultSetInt = resultSet.getInt(1);
//        }
//
//    }
//


    private DataSource getDataSource(JdbcDatabaseContainer<?> container) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(container.getJdbcUrl());
        hikariConfig.setUsername(container.getUsername());
        hikariConfig.setPassword(container.getPassword());
        hikariConfig.setDriverClassName(container.getDriverClassName());
        return new HikariDataSource(hikariConfig);
    }

}
