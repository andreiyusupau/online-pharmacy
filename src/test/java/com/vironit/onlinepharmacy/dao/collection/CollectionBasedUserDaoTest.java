package com.vironit.onlinepharmacy.dao.collection;

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
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CollectionBasedUserDaoTest {

    private User user;

    @Mock
    private IdGenerator idGenerator;
    @InjectMocks
    private CollectionBasedUserDao userDao;

    @BeforeEach
    void set() {
        user = new User(-1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test@email.com", "testpass123", Role.CONSUMER);
    }

    @Test
    void testGetByEmail() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        userDao.add(user);

        User actualUser = userDao.getByEmail("test@email.com")
                .get();

        Assertions.assertEquals(user, actualUser);
        Assertions.assertEquals(0, user.getId());
    }

    @Test
    void testAdd() {
        when(idGenerator.getNextId())
                .thenReturn(0L);

        long idFromAdd = userDao.add(user);
        long sizeAfterAdd = userDao.getAll()
                .size();
        Assertions.assertEquals(1, sizeAfterAdd);
        Assertions.assertEquals(0, idFromAdd);
    }

    @Test
    void testGet() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        userDao.add(user);

        User actualUser = userDao.get(0)
                .get();

        Assertions.assertEquals(user, actualUser);
        Assertions.assertEquals(0, user.getId());
    }

    @Test
    void testGetShouldReturnEmptyOptional() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        userDao.add(user);

        Optional<User> actualUser = userDao.get(5);

        Assertions.assertTrue(actualUser.isEmpty());
    }

    @Test
    void testGetAll() {
        User secondUser = new User(-1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test2@email.com", "testpass123", Role.MODERATOR);
        User thirdUser = new User(-1, "testFirstName", "testMiddleName", "testLastName",
                LocalDate.now(), "test3@email.com", "testpass123", Role.PROCUREMENT_SPECIALIST);
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
    void testUpdate() {
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
    void testRemove() {
        when(idGenerator.getNextId())
                .thenReturn(0L);
        userDao.add(user);

        userDao.remove(0);

        long sizeAfterRemove = userDao.getAll()
                .size();
        Assertions.assertEquals(0, sizeAfterRemove);
    }
}
