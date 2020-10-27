package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.collection.util.IdGenerator;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CollectionBasedUserDaoTest {

    @Mock
    private IdGenerator idGenerator;
    @InjectMocks
    private CollectionBasedUserDao userDao;

    private User user;
    private User secondUser;
    private User thirdUser;

    @BeforeEach
    void set() {
        user = new User(-1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
        secondUser = new User(-1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test2@email.com", "testpass123", Role.MODERATOR);
        thirdUser = new User(-1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test3@email.com", "testpass123", Role.PROCUREMENT_SPECIALIST);
    }

    @Test
    void getByEmailShouldReturnUserWithCertainEmail() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        userDao.add(user);

        User actualUser = userDao.getByEmail("test@email.com")
                .get();

        Assertions.assertEquals(user, actualUser);
        Assertions.assertEquals(0, user.getId());
    }

    @Test
    void addShouldAddUserToCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);

        long idFromAdd = userDao.add(user);
        long sizeAfterAdd = userDao.getAll()
                .size();
        Assertions.assertEquals(1, sizeAfterAdd);
        Assertions.assertEquals(0, idFromAdd);
    }

    @Test
    void getShouldGetUserFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        userDao.add(user);

        User actualUser = userDao.get(0)
                .get();

        Assertions.assertEquals(user, actualUser);
        Assertions.assertEquals(0, user.getId());
    }

    @Test
    void getShouldReturnEmptyOptional() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        userDao.add(user);

        Optional<User> actualUser = userDao.get(5);

        Assertions.assertTrue(actualUser.isEmpty());
    }

    @Test
    void getAllShouldGetAllUsersFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        userDao.add(user);
        userDao.add(secondUser);
        userDao.add(thirdUser);

        long actualSize = userDao.getAll()
                .size();
        Assertions.assertEquals(3, actualSize);
    }

    @Test
    void updateShouldUpdateUserInCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        userDao.add(user);
        User userForUpdate = new User(0, "updatedFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.MODERATOR);

        userDao.update(userForUpdate);

        User updatedUser = userDao.get(0)
                .get();
        Assertions.assertEquals(userForUpdate, updatedUser);
    }

    @Test
    void removeShouldRemoveUserFromCollection() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        userDao.add(user);

        userDao.remove(0);

        long sizeAfterRemove = userDao.getAll()
                .size();
        Assertions.assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualZero() {
        long totalElements = userDao.getTotalElements();
        Assertions.assertEquals(0, totalElements);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualTwo() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L);
        userDao.add(user);
        userDao.add(user);
        long totalElements = userDao.getTotalElements();
        Assertions.assertEquals(2, totalElements);
    }

    @Test
    void getPageShouldReturnASecondPageWithOneElement() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        userDao.add(user);
        userDao.add(secondUser);
        userDao.add(thirdUser);

        Collection<User> userPage = userDao.getPage(2, 2);

        Collection<User> expectedUserPage = new ArrayList<>();
        expectedUserPage.add(thirdUser);
        Assertions.assertEquals(expectedUserPage, userPage);
    }

    @Test
    void getPageShouldReturnAThirdPageWithNoElements() {
        when(idGenerator.getNextId())
                .thenReturn(0L)
                .thenReturn(1L)
                .thenReturn(2L);
        userDao.add(user);
        userDao.add(secondUser);
        userDao.add(thirdUser);

        Collection<User> userPage = userDao.getPage(3, 2);

        Collection<User> expectedUserPage = new ArrayList<>();
        Assertions.assertEquals(expectedUserPage, userPage);
    }
}
