/* (C)2023 */
package com.studies.zamproject.services;

import com.studies.zamproject.dtos.RegistrationRequest;
import com.studies.zamproject.dtos.UserDto;
import com.studies.zamproject.entities.User;
import com.studies.zamproject.entities.UserWithToken;
import com.studies.zamproject.exceptions.BadRequestException;
import com.studies.zamproject.exceptions.DataConflictException;
import com.studies.zamproject.exceptions.NotFoundException;
import com.studies.zamproject.repositories.UserRepository;
import com.studies.zamproject.repositories.UserWithTokenRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserWithTokenRepository userWithTokenRepository;

    @Value("${app.organizer-role}")
    private String organizerRole;

    @Transactional(rollbackFor = Exception.class)
    public UserDto registerOrganizer(RegistrationRequest registrationRequest) {
        var optionalUser = userRepository.findByEmail(registrationRequest.getEmail());
        if (optionalUser.isPresent()) {
            throw BadRequestException.userAlreadyExistsException(registrationRequest.getEmail());
        }
        var userWithToken =
                UserWithToken.builder()
                        .name(registrationRequest.getName())
                        .telephone(registrationRequest.getTelephone())
                        .email(registrationRequest.getEmail())
                        .password(passwordEncoder.encode(registrationRequest.getPassword()))
                        .token(UUID.randomUUID().toString())
                        .role(organizerRole)
                        .build();
        userWithTokenRepository.save(userWithToken);

        emailService.sendVerificationEmail(registrationRequest.getEmail());
        return UserDto.builder()
                .email(userWithToken.getEmail())
                .telephone(userWithToken.getTelephone())
                .name(userWithToken.getName())
                .build();
    }

    public void activate(String token) {
        var userWithToken =
                userWithTokenRepository
                        .findByToken(token)
                        .orElseThrow(() -> NotFoundException.userNotFound(token));

        validateAccountActivationRequest(userWithToken);

        userRepository.save(
                User.builder()
                        .name(userWithToken.getName())
                        .telephone(userWithToken.getTelephone())
                        .role(userWithToken.getRole())
                        .email(userWithToken.getEmail())
                        .password(userWithToken.getPassword())
                        .build());
    }

    private void validateAccountActivationRequest(UserWithToken userRegistrationEntity) {
        if (userRepository.findByEmail(userRegistrationEntity.getEmail()).isPresent()) {
            throw DataConflictException.userIsAlreadyActivated(userRegistrationEntity.getEmail());
        }
    }
}
