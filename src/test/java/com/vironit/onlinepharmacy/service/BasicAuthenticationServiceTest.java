package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.dao.AuthenticationDAO;
import com.vironit.onlinepharmacy.dao.CollectionBasedAuthenticationDAO;
import com.vironit.onlinepharmacy.dto.UserLoginParameters;
import com.vironit.onlinepharmacy.dto.UserPublicParameters;
import com.vironit.onlinepharmacy.dto.UserRegisterParameters;
import com.vironit.onlinepharmacy.exception.LoginException;
import com.vironit.onlinepharmacy.exception.RegistrationException;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.security.PBKDF2PasswordHasher;
import com.vironit.onlinepharmacy.security.PasswordHasher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class BasicAuthenticationServiceTest {

    @Test
    void testRegisterShouldReturnIdEqualToZero() throws RegistrationException {
        AuthenticationDAO authenticationDAO = new CollectionBasedAuthenticationDAO();
        PasswordHasher passwordHasher = new PBKDF2PasswordHasher();
        AuthenticationService authenticationService = new BasicAuthenticationService(authenticationDAO, passwordHasher);
        UserRegisterParameters userRegisterParameters = new UserRegisterParameters("testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123");
        long id = authenticationService.register(userRegisterParameters);

        Assertions.assertEquals(0, id);
    }

    @Test
    void testRegisterShouldThrowRegistrationExceptionUserWithSuchEmailAlreadyExists() throws RegistrationException {
        AuthenticationDAO authenticationDAO = new CollectionBasedAuthenticationDAO();
        PasswordHasher passwordHasher = new PBKDF2PasswordHasher();
        AuthenticationService authenticationService = new BasicAuthenticationService(authenticationDAO, passwordHasher);
        UserRegisterParameters userRegisterParameters = new UserRegisterParameters("testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123");
        authenticationService.register(userRegisterParameters);
        Exception exception = Assertions.assertThrows(RegistrationException.class, () -> {
            authenticationService.register(userRegisterParameters);
        });
        String expectedMessage = "User with email " + userRegisterParameters.getEmail() + " already exists.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testLoginShouldReturnUserPublicParameters() throws LoginException, RegistrationException {
        AuthenticationDAO authenticationDAO = new CollectionBasedAuthenticationDAO();
        PasswordHasher passwordHasher = new PBKDF2PasswordHasher();
        AuthenticationService authenticationService = new BasicAuthenticationService(authenticationDAO, passwordHasher);
        UserRegisterParameters userRegisterParameters = new UserRegisterParameters("testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123");
        authenticationService.register(userRegisterParameters);
        UserLoginParameters userLoginParameters = new UserLoginParameters("test@test.com", "testPassword123");
        UserPublicParameters userPublicParameters = authenticationService.login(userLoginParameters);
        UserPublicParameters expectedUserPublicParameters = new UserPublicParameters(0, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", Role.CONSUMER);
        Assertions.assertEquals(expectedUserPublicParameters, userPublicParameters);
    }

    @Test
    void testLoginShouldThrowExceptionEmailDoesNotExist() throws RegistrationException {
        AuthenticationDAO authenticationDAO = new CollectionBasedAuthenticationDAO();
        PasswordHasher passwordHasher = new PBKDF2PasswordHasher();
        AuthenticationService authenticationService = new BasicAuthenticationService(authenticationDAO, passwordHasher);
        UserRegisterParameters userRegisterParameters = new UserRegisterParameters("testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123");
        authenticationService.register(userRegisterParameters);
        UserLoginParameters userLoginParameters = new UserLoginParameters("nonexistentemail@test.com", "testPassword123");

        Exception exception = Assertions.assertThrows(LoginException.class, () -> {
            UserPublicParameters userPublicParameters = authenticationService.login(userLoginParameters);
        });

        String expectedMessage = "User with email " + userLoginParameters.getEmail() + " does not exist.";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testLoginShouldThrowExceptionWrongPassword() throws RegistrationException {
        AuthenticationDAO authenticationDAO = new CollectionBasedAuthenticationDAO();
        PasswordHasher passwordHasher = new PBKDF2PasswordHasher();
        AuthenticationService authenticationService = new BasicAuthenticationService(authenticationDAO, passwordHasher);
        UserRegisterParameters userRegisterParameters = new UserRegisterParameters("testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", "testPassword123");
        authenticationService.register(userRegisterParameters);
        UserLoginParameters userLoginParameters = new UserLoginParameters("test@test.com", "wrongPassword");
        Exception exception = Assertions.assertThrows(LoginException.class, () -> {
            UserPublicParameters userPublicParameters = authenticationService.login(userLoginParameters);
        });
        String expectedMessage = "Wrong password for user " + userLoginParameters.getEmail();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
