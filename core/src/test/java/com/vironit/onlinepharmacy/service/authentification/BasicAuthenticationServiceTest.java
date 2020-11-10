package com.vironit.onlinepharmacy.service.authentification;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.dto.UserDto;
import com.vironit.onlinepharmacy.dto.UserLoginDto;
import com.vironit.onlinepharmacy.vo.UserPublicVo;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.security.PasswordHasher;
import com.vironit.onlinepharmacy.service.authentication.BasicAuthenticationService;
import com.vironit.onlinepharmacy.service.exception.AuthenticationServiceException;
import com.vironit.onlinepharmacy.util.Converter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
@Disabled
@ExtendWith(MockitoExtension.class)
public class BasicAuthenticationServiceTest {
    @Mock
    private UserDao userDao;
    @Mock
    private PasswordHasher passwordHasher;
    @Mock
    private Converter<UserPublicVo, User> userToUserPublicParametersConverter;
    @Mock
    private Converter<User, UserDto> userRegisterParametersToUserConverter;
    @InjectMocks
    private BasicAuthenticationService authenticationService;

    private UserDto userDto;

    private User user;

    @BeforeEach
    void set() {
        userDto = new UserDto("testFirstName",
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
        when(userRegisterParametersToUserConverter.convert(userDto))
                .thenReturn(user);
        when(userDao.add(user))
                .thenReturn(0L);
        when(passwordHasher.hashPassword("testPassword123"))
                .thenReturn("testPassword123");

        long id = authenticationService.register(userDto);

        verify(userRegisterParametersToUserConverter).convert(userDto);
        verify(userDao).getByEmail("test@test.com");
        Assertions.assertEquals(0, id);
    }

    @Test
    void registerShouldThrowRegistrationExceptionUserWithSuchEmailAlreadyExists() {
        when(userRegisterParametersToUserConverter.convert(userDto))
                .thenReturn(user);
        when(userDao.getByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        Exception exception = Assertions.assertThrows(AuthenticationServiceException.class,
                () -> authenticationService.register(userDto));
//TODO:Not working properly
        verify(userRegisterParametersToUserConverter).convert(userDto);
        verify(userDao).getByEmail("test@test.com");
        String expectedMessage = "User with email " + userDto.getEmail() + " already exists.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void loginShouldReturnUserPublicParameters() {
        UserPublicVo expectedUserPublicVo = new UserPublicVo(0, "testFirstName",
                "testMiddleName", "testLastName", LocalDate.of(2000, 12, 12),
                "test@test.com", Role.CONSUMER);
        when(userDao.getByEmail("test@test.com"))
                .thenReturn(Optional.of(user));
        when(passwordHasher.validatePassword("testPassword123", "testPassword123"))
                .thenReturn(true);
        when(userToUserPublicParametersConverter.convert(user))
                .thenReturn(expectedUserPublicVo);
        UserLoginDto userLoginDto = new UserLoginDto("test@test.com", "testPassword123");

        UserPublicVo userPublicVo = authenticationService.login(userLoginDto);
//TODO:Not working properly
        verify(userToUserPublicParametersConverter).convert(user);
        verify(userDao).getByEmail("test@test.com");
        verify(passwordHasher).validatePassword("testPassword123", "testPassword123");

        Assertions.assertEquals(expectedUserPublicVo, userPublicVo);
    }

    @Test
    void loginShouldThrowExceptionEmailDoesNotExist() {
        UserLoginDto userLoginDto = new UserLoginDto("nonexistentemail@test.com",
                "testPassword123");
        when(userDao.getByEmail("nonexistentemail@test.com"))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(AuthenticationServiceException.class,
                () -> authenticationService.login(userLoginDto));

        verify(userToUserPublicParametersConverter, never()).convert(any(User.class));
        verify(userDao).getByEmail("nonexistentemail@test.com");
        verify(passwordHasher, never()).validatePassword(anyString(), anyString());
        verify(userDao, never()).add(any(User.class));
        String expectedMessage = "User with email " + userLoginDto.getEmail() + " does not exist.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void loginShouldThrowExceptionWrongPassword() {
        UserLoginDto userLoginDto = new UserLoginDto("test@test.com", "wrongPassword");
        when(userDao.getByEmail("test@test.com"))
                .thenReturn(Optional.of(user));
        when(passwordHasher.validatePassword("wrongPassword", "testPassword123"))
                .thenReturn(false);

        Exception exception = Assertions.assertThrows(AuthenticationServiceException.class,
                () -> authenticationService.login(userLoginDto));

        verify(userToUserPublicParametersConverter, never()).convert(any(User.class));
        verify(userDao).getByEmail("test@test.com");
        verify(passwordHasher).validatePassword("wrongPassword", "testPassword123");
        verify(userDao, never()).add(any(User.class));

        String expectedMessage = "Wrong password for user " + userLoginDto.getEmail();
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
