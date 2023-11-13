/* (C)2023 */
package com.studies.zamproject.configuration;

import com.studies.zamproject.configuration.config.AppConfig;
import com.studies.zamproject.exceptions.NotFoundException;
import com.studies.zamproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    private final AppConfig appConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (requests) ->
                                requests.requestMatchers(
                                                "/swagger-ui/**",
                                                "/swagger-ui/*",
                                                "/swagger-ui",
                                                "/swagger-ui.html",
                                                "/swagger-ui/index.html",
                                                "/v3/api-docs",
                                                "/v3/api-docs/**")
                                        .permitAll()
                                        .requestMatchers(
                                                "/auth/login",
                                                "/auth/logout",
                                                "/registration/base/activate/*",
                                                "/registration/base",
                                                "/registration/organizer")
                                        .permitAll()
                                        .requestMatchers("/registration/organizer/activate/*")
                                        .hasAnyAuthority(appConfig.getAdminRole())
                                        .requestMatchers(
                                                HttpMethod.GET, "/events", "/events/*", "/tags")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.POST, "/events")
                                        .hasAnyAuthority(
                                                appConfig.getAdminRole(),
                                                appConfig.getOrganizerRole())
                                        .requestMatchers(HttpMethod.PUT, "/events/*")
                                        .hasAnyAuthority(
                                                appConfig.getAdminRole(),
                                                appConfig.getOrganizerRole())
                                        .requestMatchers(HttpMethod.DELETE, "/events/*")
                                        .hasAnyAuthority(
                                                appConfig.getAdminRole(),
                                                appConfig.getOrganizerRole())
                                        .anyRequest()
                                        .authenticated());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username ->
                userRepository
                        .findByEmail(username)
                        .orElseThrow(() -> NotFoundException.userNotFound(username));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
