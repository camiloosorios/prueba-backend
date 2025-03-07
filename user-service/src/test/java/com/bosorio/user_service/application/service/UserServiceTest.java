package com.bosorio.user_service.application.service;

import com.bosorio.user_service.application.dto.LoginUserDto;
import com.bosorio.user_service.application.dto.RegisterUserDto;
import com.bosorio.user_service.application.dto.UserDto;
import com.bosorio.user_service.application.exception.BadRequestException;
import com.bosorio.user_service.application.exception.NotFoundException;
import com.bosorio.user_service.domain.enums.Role;
import com.bosorio.user_service.domain.model.User;
import com.bosorio.user_service.domain.port.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private RegisterUserDto registerUserDto;
    private LoginUserDto loginUserDto;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerUserDto = new RegisterUserDto();
        registerUserDto.setName("Test User");
        registerUserDto.setEmail("test@example.com");
        registerUserDto.setPassword("password");
        registerUserDto.setPasswordConfirmation("password");

        loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("test@example.com");
        loginUserDto.setPassword("password");

        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setRole(Role.USER);
        user.setPassword("encodedPassword");

        userDto = new UserDto(1L, "Test User", "test@example.com");
    }

    @Test
    void registerShouldRegisterUser() {
        when(userPersistencePort.findUserByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        doNothing().when(userPersistencePort).register(any(User.class));

        userService.register(registerUserDto);

        verify(userPersistencePort, times(1)).findUserByEmail(anyString());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userPersistencePort, times(1)).register(any(User.class));
    }

    @Test
    void registerShouldThrowBadRequestExceptionWhenPasswordsDoNotMatch() {
        registerUserDto.setPasswordConfirmation("differentPassword");

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.register(registerUserDto);
        });

        assertEquals("Passwords do not match", exception.getMessage());
        verify(userPersistencePort, never()).findUserByEmail(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userPersistencePort, never()).register(any(User.class));
    }

    @Test
    void registerShouldThrowBadRequestExceptionWhenUserAlreadyExists() {
        when(userPersistencePort.findUserByEmail(anyString())).thenReturn(Optional.of(user));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.register(registerUserDto);
        });

        assertEquals("User already exists", exception.getMessage());
        verify(userPersistencePort, times(1)).findUserByEmail(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userPersistencePort, never()).register(any(User.class));
    }

    @Test
    void loginShouldReturnToken() {
        when(userPersistencePort.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtService.createToken(anyString(), any(Role.class))).thenReturn("token");

        String result = userService.login(loginUserDto);

        assertNotNull(result);
        assertEquals("token", result);

        verify(userPersistencePort, times(1)).findUserByEmail(anyString());
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        verify(jwtService, times(1)).createToken(anyString(), any(Role.class));
    }

    @Test
    void loginShouldThrowBadRequestExceptionWhenEmailOrPasswordIsIncorrect() {
        when(userPersistencePort.findUserByEmail(anyString())).thenReturn(Optional.empty());

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.login(loginUserDto);
        });

        assertEquals("Email or password is incorrect", exception.getMessage());
        verify(userPersistencePort, times(1)).findUserByEmail(anyString());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).createToken(anyString(), any(Role.class));
    }

    @Test
    void loginShouldThrowBadRequestExceptionWhenPasswordIsIncorrect() {
        when(userPersistencePort.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.login(loginUserDto);
        });

        assertEquals("Email or password is incorrect", exception.getMessage());
        verify(userPersistencePort, times(1)).findUserByEmail(anyString());
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        verify(jwtService, never()).createToken(anyString(), any(Role.class));
    }

    @Test
    void findUserByIdShouldReturnUserDto() {
        when(userPersistencePort.findUserById(anyLong())).thenReturn(Optional.of(user));

        UserDto result = userService.findUserById(1L);

        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getName(), result.getName());
        assertEquals(userDto.getEmail(), result.getEmail());

        verify(userPersistencePort, times(1)).findUserById(anyLong());
    }

    @Test
    void findUserByIdShouldThrowNotFoundException() {
        when(userPersistencePort.findUserById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.findUserById(1L);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userPersistencePort, times(1)).findUserById(anyLong());
    }
}