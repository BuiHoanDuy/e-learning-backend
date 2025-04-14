package com.example.start.configuratioin;

import com.example.start.entity.Role;
import com.example.start.entity.User;
import com.example.start.repository.PermissionRepository;
import com.example.start.repository.RoleRepository;
import com.example.start.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Bean
    public ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("ADMIN").isEmpty()) {

                // Tìm hoặc tạo mới Role ADMIN
                Role role = roleRepository.findByName("ADMIN")
                        .orElseGet(() -> {
                            Role newRole = Role.builder()
                                    .name("ADMIN")
                                    .description("ROLE ADMIN")
                                    .permissions(new HashSet<>(permissionRepository.findAllById(Set.of("POST", "DELETE", "UPDATE", "APPROVE"))))
                                    .build();
                            return roleRepository.save(newRole);
                        });
                User user = User.builder()
                        .username("ADMIN")
                        .password(passwordEncoder.encode("ADMIN"))
                        .roles(Set.of(role))
                        .createdAt(LocalDateTime.now())
                        .build();

                userRepository.save(user);
                System.out.println("⚠️ ADMIN user created with default password: ADMIN");
            }
        };
    }
}