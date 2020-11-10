package com.vironit.onlinepharmacy.service.user;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.dto.UserDto;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.exception.UserServiceException;
import com.vironit.onlinepharmacy.vo.UserPublicVo;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@Disabled
@ExtendWith(MockitoExtension.class)
public class BasicUserServiceTest {

    @Mock
    private UserDao userDao;
    @InjectMocks
    private BasicUserService userService;

    private UserDto userDto;
    private User user;
    private UserPublicVo userPublicVo;

    @BeforeEach
    void set() {
        userDto =new UserDto( "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123", "testPassword123");
        user = new User(0, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123", Role.CONSUMER);
        userPublicVo=new UserPublicVo(1, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", Role.CONSUMER);
    }

    @Test
    void addShouldUseDao() {
        when(userDao.add(any()))
                .thenReturn(0L);

        long id = userService.add(userDto);

        verify(userDao).add(user);
        Assertions.assertEquals(0, id);
    }

    @Test
    void getShouldNotThrowException() {
        when(userDao.get(anyLong()))
                .thenReturn(Optional.of(user));

        UserPublicVo actualUserPublicVo = userService.get(1);

        verify(userDao).get(1);
        Assertions.assertEquals(userPublicVo, actualUserPublicVo);
    }

    @Test
    void getShouldThrowException() {
        when(userDao.get(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(UserServiceException.class, () -> userService.get(1));

        verify(userDao).get(1);
        String expectedMessage = "Can't get user. User with id " + 1 + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getAllShouldNotThrowException() {
        Collection<User> users = new ArrayList<>();
        users.add(user);

        when(userDao.getAll())
                .thenReturn(users);

        Collection<UserPublicVo> actualUsers = userService.getAll();

        Assertions.assertEquals(users, actualUsers);
    }

    @Test
    void updateShouldUseDao() {
        UserDto userDtoForUpdate = new UserDto("updatedFirstName",
                "updatedMiddleName", "updatedLastName", LocalDate.of(1999, 12, 12),
                "test@test2.com", "testPass4word123","\"testPass4word123\"");
        User userForUpdate = new User(0, "updatedFirstName",
                "updatedMiddleName", "updatedLastName", LocalDate.of(1999, 12, 12),
                "test@test2.com", "testPass4word123", Role.MODERATOR);
        when(userDao.update(any()))
                .thenReturn(true);

        userService.update(userDtoForUpdate);

        verify(userDao).update(userForUpdate);
    }

    @Test
    void removeShouldUseDao() {
        when(userDao.remove(anyLong()))
                .thenReturn(true);

        userService.remove(1);

        verify(userDao).remove(1);
    }
}
