package com.example.start.dto.request.user;

import com.example.start.validator.UniqueEmail.UniqueEmailConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String fullName;
    @Length(min = 4, message = "INVALID_PASSWORD")
    String password;
    @Email(message = "INVALID_EMAIL")
    @UniqueEmailConstraint
    @NotBlank
    String email;
    List<String> roles;
}
