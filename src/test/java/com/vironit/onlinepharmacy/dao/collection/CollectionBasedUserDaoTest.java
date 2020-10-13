package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CollectionBasedUserDaoTest {
    @Mock
    User firstInputUser;
    @Mock
    User secondInputUser;
    @Mock
    User thirdInputUser;
@Mock
    IdGenerator idGenerator;
@InjectMocks
   CollectionBasedUserDao collectionBasedUserDao;

    @Test
    void testAdd() {
        long id = 1;
        when(idGenerator.getNextId()).thenReturn(id);

       long idFromAdd= collectionBasedUserDao.add(any(User.class));

        Assertions.assertEquals(id,idFromAdd);
    }

    @Test
    void testGet() {

        long id = 1;
        when(idGenerator.getNextId()).thenReturn(id);
        collectionBasedUserDao.add(firstInputUser);

        User user=collectionBasedUserDao.get(id).get();

        Assertions.assertEquals(user,firstInputUser);
        Assertions.assertEquals(1,user.getId());
    }

    @Test
    void testGetShouldReturnEmptyOptional() {

        long id = 1;
        when(idGenerator.getNextId()).thenReturn(id);
        collectionBasedUserDao.add(firstInputUser);

        long wrongId=5;
        Optional<User> user=collectionBasedUserDao.get(wrongId);

        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    void testGetAll() {
        when(idGenerator.getNextId())
                .thenReturn(1L)
                .thenReturn(2L)
                .thenReturn(3L);
        collectionBasedUserDao.add(firstInputUser);
        collectionBasedUserDao.add(secondInputUser);
        collectionBasedUserDao.add(thirdInputUser);
        long expectedSize=3;
long actualSize=collectionBasedUserDao.getAll().size();
        Assertions.assertEquals(expectedSize,actualSize);
    }

    @Test
    void update() {

    }

    @Test
    void remove() {

    }

    @Test
    void getByEmail() {

    }

}
