package com.bosorio.user_service.application.service;

import com.bosorio.user_service.application.dto.LoginUserDto;
import com.bosorio.user_service.application.dto.RegisterUserDto;
import com.bosorio.user_service.application.dto.UserDto;
import com.bosorio.user_service.application.exception.BadRequestException;
import com.bosorio.user_service.application.exception.NotFoundException;
import com.bosorio.user_service.application.usecases.UserUseCases;
import com.bosorio.user_service.domain.enums.Role;
import com.bosorio.user_service.domain.model.User;
import com.bosorio.user_service.domain.port.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCases {

    private final UserPersistencePort userPersistencePort;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterUserDto userDto) {

        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            throw new BadRequestException("Passwords do not match");
        }

        Optional<User> userExists = userPersistencePort.findUserByEmail(userDto.getEmail());

        if (userExists.isPresent()) {
            throw new BadRequestException("User already exists");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userPersistencePort.register(user);
    }

    @Override
    public String login(LoginUserDto userDto) {
        User user = userPersistencePort.findUserByEmail(userDto.getEmail())
                .orElseThrow(() -> new BadRequestException("Email or password is incorrect"));

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new BadRequestException("Email or password is incorrect");
        }

        return jwtService.createToken(String.valueOf(user.getId()), user.getRole());
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userPersistencePort.findUserById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
