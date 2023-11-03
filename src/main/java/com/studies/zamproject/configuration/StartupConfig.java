/* (C)2023 */
package com.studies.zamproject.configuration;

import com.studies.zamproject.entities.User;
import com.studies.zamproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StartupConfig {

    @Value("${app.admin-role}")
    private String adminRole;

    @Bean
    public CommandLineRunner initData(UserRepository userRepository) {
        return (args) -> {
            userRepository.save(
                    User.builder()
                            .email("test@test.com")
                            .name("test")
                            .password(
                                    "$2a$12$1ZdVtupqu72K7kUDOu4uVumukogroUEDJYVyihQgJdTAP7uNSFEoK")
                            .role(adminRole)
                            .telephone("123456789")
                            .build());
        };
    }
}
