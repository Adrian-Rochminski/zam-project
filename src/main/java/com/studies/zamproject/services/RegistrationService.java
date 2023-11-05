/* (C)2023 */
package com.studies.zamproject.services;

import com.studies.zamproject.dtos.RegistrationRequest;
import com.studies.zamproject.dtos.UserDTO;
import com.studies.zamproject.entities.User;
import com.studies.zamproject.entities.UserWithToken;
import com.studies.zamproject.exceptions.BadRequestException;
import com.studies.zamproject.exceptions.DataConflictException;
import com.studies.zamproject.exceptions.InternalServerError;
import com.studies.zamproject.exceptions.NotFoundException;
import com.studies.zamproject.repositories.UserRepository;
import com.studies.zamproject.repositories.UserWithTokenRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserWithTokenRepository userWithTokenRepository;

    @Value("${app.organizer-role}")
    private String organizerRole;

    @Transactional(rollbackFor = Exception.class)
    public UserDTO registerOrganizer(RegistrationRequest registrationRequest) {
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

        try {
            emailService.sendVerificationEmail(registrationRequest.getEmail());
            return UserDTO.builder()
                    .email(userWithToken.getEmail())
                    .telephone(userWithToken.getTelephone())
                    .name(userWithToken.getName())
                    .build();
        } catch (Exception e) {
            log.error("There was a problem with sending email", e);
            throw InternalServerError.couldNotSendEmail(userWithToken.getEmail());
        }
    }

    public void activate(String token) {
        var userWithToken =
                userWithTokenRepository
                        .findByToken(token)
                        .orElseThrow(() -> NotFoundException.userWithTokenNotFound(token));

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
