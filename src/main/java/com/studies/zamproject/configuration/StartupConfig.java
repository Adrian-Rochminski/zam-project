/* (C)2023 */
package com.studies.zamproject.configuration;

import com.studies.zamproject.configuration.config.AppConfig;
import com.studies.zamproject.entities.Tag;
import com.studies.zamproject.entities.User;
import com.studies.zamproject.repositories.TagRepository;
import com.studies.zamproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StartupConfig {

    private final AppConfig appConfig;

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, TagRepository tagRepository) {
        return (args) -> {
            userRepository.save(
                    User.builder()
                            .email("test@test.com")
                            .name("test")
                            .password(
                                    "$2a$12$1ZdVtupqu72K7kUDOu4uVumukogroUEDJYVyihQgJdTAP7uNSFEoK")
                            .role(appConfig.getAdminRole())
                            .telephone("123456789")
                            .build());
            appConfig
                    .getTags()
                    .forEach(tagName -> tagRepository.save(Tag.builder().name(tagName).build()));
        };
    }
}
