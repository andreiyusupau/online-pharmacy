package com.vironit.onlinepharmacy.service.authentification;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.dto.UserLoginParameters;
import com.vironit.onlinepharmacy.dto.UserPublicParameters;
import com.vironit.onlinepharmacy.dto.UserRegisterParameters;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.security.PasswordHasher;
import com.vironit.onlinepharmacy.service.authentication.BasicAuthenticationService;
import com.vironit.onlinepharmacy.service.authentication.exception.LoginException;
import com.vironit.onlinepharmacy.service.authentication.exception.RegistrationException;
import com.vironit.onlinepharmacy.util.UserRegisterParametersToUserConverter;
import com.vironit.onlinepharmacy.util.UserToUserPublicParametersConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BasicAuthenticationServiceTest {

    @Mock
    UserToUserPublicParametersConverter userToUserPublicParametersConverter;
    @Mock
    UserRegisterParametersToUserConverter userRegisterParametersToUserConverter;
    @Mock
    UserDao userDao;
    @Mock
    PasswordHasher passwordHasher;
    @InjectMocks
    BasicAuthenticationService authenticationService;

    static UserRegisterParameters userRegisterParameters;

    @BeforeAll
    static void init() {
        userRegisterParameters = new UserRegisterParameters("testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123");

    }

    @Test
    void testRegisterShouldReturnIdEqualToZero() throws RegistrationException {
        User user = new User(-1, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123", Role.CONSUMER);
        when(userDao.getByEmail("test@test.com"))
                .thenReturn(Optional.empty());
        when(userRegisterParametersToUserConverter.convert(userRegisterParameters))
                .thenReturn(user);
        when(userDao.add(user)).thenReturn(0L);
        when(passwordHasher.hashPassword("testPassword123"))
                .thenReturn("testPassword123");

        long id = authenticationService.register(userRegisterParameters);

        Assertions.assertEquals(0, id);
    }

    @Test
    void testRegisterShouldThrowRegistrationExceptionUserWithSuchEmailAlreadyExists() {
        User user = new User(-1, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123", Role.CONSUMER);
        when(userDao.getByEmail("test@test.com"))
                .thenReturn(Optional.of(user));
        when(userRegisterParametersToUserConverter.convert(userRegisterParameters))
                .thenReturn(user);

        Exception exception = Assertions.assertThrows(RegistrationException.class, () -> {
            authenticationService.register(userRegisterParameters);
        });

        String expectedMessage = "User with email " + userRegisterParameters.getEmail() + " already exists.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testLoginShouldReturnUserPublicParameters() throws LoginException {
        User user = new User(0, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123", Role.CONSUMER);
        UserPublicParameters expectedUserPublicParameters = new UserPublicParameters(0, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", Role.CONSUMER);
        when(userDao.getByEmail("test@test.com"))
                .thenReturn(Optional.of(user));
        when(passwordHasher.validatePassword("testPassword123", "testPassword123"))
                .thenReturn(true);
        when(userToUserPublicParametersConverter.convert(user))
                .thenReturn(expectedUserPublicParameters);
        UserLoginParameters userLoginParameters = new UserLoginParameters("test@test.com", "testPassword123");

        UserPublicParameters userPublicParameters = authenticationService.login(userLoginParameters);

        Assertions.assertEquals(expectedUserPublicParameters, userPublicParameters);
    }

    @Test
    void testLoginShouldThrowExceptionEmailDoesNotExist() {
        UserLoginParameters userLoginParameters = new UserLoginParameters("nonexistentemail@test.com",
                "testPassword123");
        when(userDao.getByEmail("nonexistentemail@test.com"))
                .thenReturn(Optional.empty());
        Exception exception = Assertions.assertThrows(LoginException.class, () -> {
            authenticationService.login(userLoginParameters);
        });

        String expectedMessage = "User with email " + userLoginParameters.getEmail() + " does not exist.";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testLoginShouldThrowExceptionWrongPassword() {
        User user = new User(0, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123", Role.CONSUMER);
        UserLoginParameters userLoginParameters = new UserLoginParameters("test@test.com", "wrongPassword");
        when(userDao.getByEmail("test@test.com"))
                .thenReturn(Optional.of(user));
        when(passwordHasher.validatePassword("wrongPassword", "testPassword123"))
                .thenReturn(false);

        Exception exception = Assertions.assertThrows(LoginException.class, () -> {
            authenticationService.login(userLoginParameters);
        });

        String expectedMessage = "Wrong password for user " + userLoginParameters.getEmail();
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
