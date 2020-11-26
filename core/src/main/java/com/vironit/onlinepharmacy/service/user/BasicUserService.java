package com.vironit.onlinepharmacy.service.user;

import com.vironit.onlinepharmacy.dto.UserDto;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.repository.UserRepository;
import com.vironit.onlinepharmacy.service.exception.UserServiceException;
import com.vironit.onlinepharmacy.util.converter.Converter;
import com.vironit.onlinepharmacy.vo.UserPublicVo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class BasicUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Converter<User, UserDto> userDtoToUserConverter;
    private final Converter<UserPublicVo, User> userToUserPublicVoConverter;

    public BasicUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, Converter<User, UserDto> userDtoToUserConverter, Converter<UserPublicVo, User> userToUserPublicVoConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.userToUserPublicVoConverter = userToUserPublicVoConverter;
    }

    @Override
    public long add(UserDto userDto) {
        User user = userDtoToUserConverter.convert(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user)
                .getId();
    }

    @Override
    public UserPublicVo get(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserServiceException("Can't get user. User with id " + id + " not found."));
        return userToUserPublicVoConverter.convert(user);
    }

    @Override
    public Collection<UserPublicVo> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userToUserPublicVoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void update(UserDto userDto) {
        User user = userDtoToUserConverter.convert(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(userDtoToUserConverter.convert(userDto));
    }

    @Override
    public void remove(long id) {
        userRepository.deleteById(id);
    }
}
