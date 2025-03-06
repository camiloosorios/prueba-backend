package com.bosorio.order_management_service.infrastructure.adapter.out.service;

import com.bosorio.order_management_service.application.dto.user.UserDto;
import com.bosorio.order_management_service.infrastructure.adapter.out.client.UserClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserClient userClient;

    private final HttpServletRequest request;

    public UserDto getUserById(Long id) {
        String token = request.getHeader("Authorization");
        System.out.println(token);
        return userClient.getUserById(id, token);
    }

}
