package com.vironit.onlinepharmacy.service.authentification;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.dto.UserLoginData;
import com.vironit.onlinepharmacy.dto.UserPublicData;
import com.vironit.onlinepharmacy.dto.UserData;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.security.PasswordHasher;
import com.vironit.onlinepharmacy.service.authentication.BasicAuthenticationService;
import com.vironit.onlinepharmacy.service.exception.AuthenticationServiceException;
import com.vironit.onlinepharmacy.util.Converter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasicAuthenticationServiceTest {
    @Mock
    private UserDao userDao;
    @Mock
    private PasswordHasher passwordHasher;
    @Mock
    private Converter<UserPublicData, User> userToUserPublicParametersConverter;
    @Mock
    private Converter<User, UserData> userRegisterParametersToUserConverter;
    @InjectMocks
    private BasicAuthenticationService authenticationService;

    private UserData userData;

    private User user;

    @BeforeEach
    void set() {
        userData = new UserData(id, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123", "testPassword123");
        user = new User(0, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123", Role.CONSUMER);
    }

    @Test
    void registerShouldReturnIdEqualToZero() {
        when(userDao.getByEmail("test@test.com"))
                .thenReturn(Optional.empty());
        when(userRegisterParametersToUserConverter.convert(userData))
                .thenReturn(user);
        when(userDao.add(user))
                .thenReturn(0L);
        when(passwordHasher.hashPassword("testPassword123"))
                .thenReturn("testPassword123");

        long id = authenticationService.register(userData);

        verify(userRegisterParametersToUserConverter).convert(userData);
        verify(userDao).getByEmail("test@test.com");
        Assertions.assertEquals(0, id);
    }

    @Test
    void registerShouldThrowRegistrationExceptionUserWithSuchEmailAlreadyExists() {
        when(userRegisterParametersToUserConverter.convert(userData))
                .thenReturn(user);
        when(userDao.getByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        Exception exception = Assertions.assertThrows(AuthenticationServiceException.class,
                () -> authenticationService.register(userData));
//TODO:Not working properly
        verify(userRegisterParametersToUserConverter).convert(userData);
        verify(userDao).getByEmail("test@test.com");
        String expectedMessage = "User with email " + userData.getEmail() + " already exists.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void loginShouldReturnUserPublicParameters() {
        UserPublicData expectedUserPublicData = new UserPublicData(0, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", Role.CONSUMER);
        when(userDao.getByEmail("test@test.com"))
                .thenReturn(Optional.of(user));
        when(passwordHasher.validatePassword("testPassword123", "testPassword123"))
                .thenReturn(true);
        when(userToUserPublicParametersConverter.convert(user))
                .thenReturn(expectedUserPublicData);
        UserLoginData userLoginData = new UserLoginData("test@test.com", "testPassword123");

        UserPublicData userPublicData = authenticationService.login(userLoginData);

        verify(userToUserPublicParametersConverter).convert(user);
        verify(userDao).getByEmail("test@test.com");
        verify(passwordHasher).validatePassword("testPassword123", "testPassword123");

        Assertions.assertEquals(expectedUserPublicData, userPublicData);
    }

    @Test
    void loginShouldThrowExceptionEmailDoesNotExist() {
        UserLoginData userLoginData = new UserLoginData("nonexistentemail@test.com",
                "testPassword123");
        when(userDao.getByEmail("nonexistentemail@test.com"))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(AuthenticationServiceException.class,
                () -> authenticationService.login(userLoginData));

        verify(userToUserPublicParametersConverter, never()).convert(any(User.class));
        verify(userDao).getByEmail("nonexistentemail@test.com");
        verify(passwordHasher, never()).validatePassword(anyString(), anyString());
        verify(userDao, never()).add(any(User.class));
        String expectedMessage = "User with email " + userLoginData.getEmail() + " does not exist.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void loginShouldThrowExceptionWrongPassword() {
        UserLoginData userLoginData = new UserLoginData("test@test.com", "wrongPassword");
        when(userDao.getByEmail("test@test.com"))
                .thenReturn(Optional.of(user));
        when(passwordHasher.validatePassword("wrongPassword", "testPassword123"))
                .thenReturn(false);

        Exception exception = Assertions.assertThrows(AuthenticationServiceException.class,
                () -> authenticationService.login(userLoginData));

        verify(userToUserPublicParametersConverter, never()).convert(any(User.class));
        verify(userDao).getByEmail("test@test.com");
        verify(passwordHasher).validatePassword("wrongPassword", "testPassword123");
        verify(userDao, never()).add(any(User.class));

        String expectedMessage = "Wrong password for user " + userLoginData.getEmail();
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
