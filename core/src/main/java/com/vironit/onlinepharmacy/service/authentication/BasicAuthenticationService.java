package com.vironit.onlinepharmacy.service.authentication;

import com.vironit.onlinepharmacy.dto.UserDto;
import com.vironit.onlinepharmacy.dto.UserLoginDto;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.repository.UserRepository;
import com.vironit.onlinepharmacy.service.exception.AuthenticationServiceException;
import com.vironit.onlinepharmacy.util.converter.Converter;
import com.vironit.onlinepharmacy.vo.UserPublicVo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthenticationService implements AuthenticationService<UserDto, UserPublicVo, UserLoginDto> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Converter<UserPublicVo, User> userToUserPublicVoConverter;
    private final Converter<User, UserDto> userDataToUserConverter;

    public BasicAuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                      Converter<UserPublicVo, User> userToUserPublicVoConverter,
                                      Converter<User, UserDto> userDataToUserConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userToUserPublicVoConverter = userToUserPublicVoConverter;
        this.userDataToUserConverter = userDataToUserConverter;
    }

    @Override
    public long register(UserDto userDto) {
        User user = userDataToUserConverter.convert(userDto);
        String email = userDto.getEmail();
        if (userRepository.findByEmail(email)
                .isEmpty()) {
            String password = userDto.getPassword();
            String hashedPassword = passwordEncoder.encode(password);
            user.setPassword(hashedPassword);
            user.setRole(Role.CONSUMER);
            return userRepository.save(user)
                    .getId();
        } else {
            throw new AuthenticationServiceException("User with email " + email + " already exists.");
        }
    }

    @Override
    public UserPublicVo login(UserLoginDto userLoginDto) {
        String email = userLoginDto.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationServiceException("User with email " + email + " does not exist."));
        String password = userLoginDto.getPassword();
        if (passwordEncoder.matches(password, user.getPassword())) {
            return userToUserPublicVoConverter.convert(user);
        } else {
            throw new AuthenticationServiceException("Wrong password for user " + email);
        }
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationServiceException("User with email " + email + " does not exist."));
    }
}
