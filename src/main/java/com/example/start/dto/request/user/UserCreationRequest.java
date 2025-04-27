package com.example.start.dto.request.user;

import com.example.start.entity.Role;
import com.example.start.validator.UniqueEmail.UniqueEmailConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String fullName;
    @Email(message = "INVALID_EMAIL")
    @UniqueEmailConstraint
    @NotBlank
    String email;
    @NotBlank
    String username;
    @Length(min = 4, message = "INVALID_PASSWORD")
    String password;
}
