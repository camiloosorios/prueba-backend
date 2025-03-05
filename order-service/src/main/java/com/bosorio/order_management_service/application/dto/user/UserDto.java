package com.bosorio.order_management_service.application.dto.user;

import com.bosorio.order_management_service.domain.enums.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private Role role;
}
