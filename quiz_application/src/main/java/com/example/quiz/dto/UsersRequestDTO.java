
package com.example.quiz.dto;

import com.example.quiz.enums.Role;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsersRequestDTO {
    @NotBlank private String name;
    @Email private String email;
    @Size(min = 6) private String password;

    @NotNull(message = "User Role is required")
    private Role role;   // NotBlank can only be used for string not the Enums
}
