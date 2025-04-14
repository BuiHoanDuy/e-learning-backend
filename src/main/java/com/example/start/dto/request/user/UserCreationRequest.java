package com.example.start.dto.request.user;

import com.example.start.entity.Role;
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
    String email;
    String username;
    @Length(min = 4, message = "INVALID_PASSWORD")
    String password;
}
