package com.bosorio.user_service.application.usecases;

import com.bosorio.user_service.application.dto.LoginUserDto;
import com.bosorio.user_service.application.dto.RegisterUserDto;
import com.bosorio.user_service.application.dto.UserDto;

public interface UserUseCases {

    void register(RegisterUserDto userDto);
    String login(LoginUserDto userDto);
    UserDto findUserById(Long id);

}
