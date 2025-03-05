package com.bosorio.user_service.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterUserDto {

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email format invalid")
    private String email;

    @NotEmpty(message = "Password is required")
    @Length(min = 6, message = "Password must have at least 6 character")
    private String password;

    @NotEmpty(message = "Password confirmation is required")
    @Length(min = 6, message = "Password must have at least 6 character")
    private String passwordConfirmation;

}
