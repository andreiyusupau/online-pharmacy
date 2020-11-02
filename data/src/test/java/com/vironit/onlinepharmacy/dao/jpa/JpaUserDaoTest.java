package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
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

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
class JpaUserDaoTest {

    @Container
    private PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:13.0-alpine")
            .withDatabaseName("online_pharmacy")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private UserDao userDao;

    private User user;
    private User secondUser;
    private User thirdUser;

    @BeforeEach
    void set(){
        user = new User();
       user.setFirstName("testFirstName");
       user.setMiddleName("testMiddleName");
       user.setLastName("testLastName");
       user.setDateOfBirth(LocalDate.of(2000, 12, 12));
       user.setEmail("test@email.com");
       user.setPassword("testpass123");
       user.setRole(Role.CONSUMER);
        secondUser = new User();
        secondUser.setFirstName("testFirstName");
        secondUser.setMiddleName("testMiddleName");
        secondUser.setLastName("testLastName");
        secondUser.setDateOfBirth(LocalDate.of(2000, 10, 12));
        secondUser.setEmail("test2@email.com");
        secondUser.setPassword("testpass123");
        secondUser.setRole(Role.MODERATOR);
        thirdUser = new User();
        thirdUser.setFirstName("testFirstName");
        thirdUser.setMiddleName("testMiddleName");
        thirdUser.setLastName("testLastName");
        thirdUser.setDateOfBirth(LocalDate.of(2000, 11, 20));
        thirdUser.setEmail("test3@email.com");
        thirdUser.setPassword("testpass3213");
        thirdUser.setRole(Role.PROCUREMENT_SPECIALIST);
    }

    @Test
    void getByEmailShouldReturnUserWithCertainEmail() {
        userDao.add(user);

        User actualUser = userDao.getByEmail("test@email.com")
                .get();

        assertEquals("test@email.com", actualUser.getEmail());
    }

    @Test
    void addShouldAddUserToDatabase() {
        long id = userDao.add(user);

        assertEquals(1, id);
    }

    @Test
    void getShouldGetUserFromDatabase() {
        userDao.add(user);

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
        userDao.add(user);
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
        userDao.add(user);
        userDao.add(secondUser);
        userDao.add(thirdUser);

        userDao.remove(2);

        long sizeAfterRemove = userDao.getAll()
                .size();
        assertEquals(2, sizeAfterRemove);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualZero() {
        long totalElements = userDao.getTotalElements();

        assertEquals(0, totalElements);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualThree() {
        userDao.add(user);
        userDao.add(secondUser);
        userDao.add(thirdUser);

        long totalElements = userDao.getTotalElements();

        assertEquals(3, totalElements);
    }

    @Test
    void getPageShouldReturnASecondPageWithOneElement() {
        userDao.add(user);
        userDao.add(secondUser);
        userDao.add(thirdUser);

        Collection<User> userPage = userDao.getPage(2, 2);

        int pageSize = userPage.size();
        assertEquals(1, pageSize);
    }

    @Test
    void getPageShouldReturnAThirdPageWithNoElements() {
        userDao.add(user);
        userDao.add(secondUser);
        userDao.add(thirdUser);

        Collection<User> userPage = userDao.getPage(3, 2);

        int pageSize = userPage.size();
        assertEquals(0, pageSize);
    }

}
