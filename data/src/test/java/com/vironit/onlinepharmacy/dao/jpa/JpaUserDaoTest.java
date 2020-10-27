package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.jdbc.JdbcUserDao;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@Testcontainers
class JpaUserDaoTest {

    @Container
    private PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:13.0-alpine")
            .withDatabaseName("online_pharmacy")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private JpaUserDao userDao;

    private User user;
    private User secondUser;
    private User thirdUser;

    @BeforeEach
    void set() throws SQLException {
        userDao = new JpaUserDao();
        user = new User(1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.of(2000, 12, 12), "test@email.com", "testpass123", Role.CONSUMER);
        secondUser = new User(2, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test2@email.com", "testpass123", Role.MODERATOR);
        thirdUser = new User(3, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test3@email.com", "testpass123", Role.PROCUREMENT_SPECIALIST);
        String sql = "INSERT INTO roles (name) " +
                "VALUES('" + Role.CONSUMER.toString() + "'),('" + Role.MODERATOR.toString() + "'),('" + Role.PROCUREMENT_SPECIALIST.toString() + "');\n" +
                "INSERT INTO users (first_name, middle_name, last_name, date_of_birth, email, password, role_id) " +
                "VALUES('testFirstName','testMiddleName','testLastName','2000-12-12','test@email.com','testpass123',1);";
    }

    @Test
    void getByEmailShouldReturnUserWithCertainEmail() {
        User actualUser = userDao.getByEmail("test@email.com")
                .get();

        assertEquals("test@email.com", actualUser.getEmail());
    }

    @Test
    void addShouldAddUserToDatabase() {
        long id = userDao.add(secondUser);

        assertEquals(2, id);
    }

    @Test
    void getShouldGetUserFromDatabase() {
        User actualUser = userDao.get(1)
                .get();
        assertEquals(user.getFirstName(), actualUser.getFirstName());
        assertEquals(user.getDateOfBirth(), actualUser.getDateOfBirth());
    }

    @Test
    void getShouldReturnEmptyOptional() {
        Optional<User> actualUser = userDao.get(5);

        Assertions.assertTrue(actualUser.isEmpty());
    }

    @Test
    void getAllShouldGetAllUsersFromDatabase() {
        userDao.add(secondUser);
        userDao.add(thirdUser);

        long actualSize = userDao.getAll()
                .size();
        assertEquals(3, actualSize);
    }

    @Test
    void updateShouldUpdateUserInDatabase() {
        User userForUpdate = new User(1, "updatedFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.MODERATOR);

        userDao.update(userForUpdate);

        User updatedUser = userDao.get(1)
                .get();
        assertEquals(userForUpdate.getFirstName(), updatedUser.getFirstName());
        assertEquals(userForUpdate.getDateOfBirth(), updatedUser.getDateOfBirth());
    }

    @Test
    void removeShouldRemoveUserFromDatabase() {
        userDao.add(secondUser);
        userDao.add(thirdUser);

        userDao.remove(2);

        long sizeAfterRemove = userDao.getAll()
                .size();
        assertEquals(2, sizeAfterRemove);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualZero() {
        int totalElements = userDao.getTotalElements();

        assertEquals(1, totalElements);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualTwo() {
        userDao.add(secondUser);
        userDao.add(thirdUser);

        int totalElements = userDao.getTotalElements();

        assertEquals(3, totalElements);
    }

    @Test
    void getPageShouldReturnASecondPageWithOneElement() {
        userDao.add(secondUser);
        userDao.add(thirdUser);

        Collection<User> userPage = userDao.getPage(2, 2);

        int pageSize = userPage.size();
        assertEquals(1, pageSize);
    }

    @Test
    void getPageShouldReturnAThirdPageWithNoElements() {
        userDao.add(secondUser);
        userDao.add(thirdUser);

        Collection<User> userPage = userDao.getPage(3, 2);

        int pageSize = userPage.size();
        assertEquals(0, pageSize);
    }

}
