/* (C)2023 */
package com.studies.zamproject.services;

import com.studies.zamproject.configuration.config.AppConfig;
import com.studies.zamproject.dtos.BaseUserRegistrationRequest;
import com.studies.zamproject.dtos.OrganizerRegistrationRequest;
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

    private final AppConfig appConfig;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public static final String ORGANIZER_REGISTRATION_ACTIVATION_MAIL =
            "Aby dokończyć proces rejestracji potrzebujesz przesłać nam dokumenty"
                    + " potwierdzające twój status podmiotu gospodarczego.";
    public static final String ORGANIZER_REGISTRATION_ACTIVATION_MAIL_SUBJECT =
            "Witaj nowy organizatorze!";

    public static final String BASE_USER_REGISTRATION_ACTIVATION_MAIL =
            "Aby dokończyć proces rejestracji kliknij w <a href=\"%s\">link</a>";
    public static final String BASE_USER_REGISTRATION_ACTIVATION_MAIL_SUBJECT =
            "Witaj nowy użytkowniku!";

    @Transactional(rollbackFor = Exception.class)
    public UserDTO registerOrganizer(OrganizerRegistrationRequest organizerRegistrationRequest) {
        var optionalUser = userRepository.findByEmail(organizerRegistrationRequest.getEmail());
        if (optionalUser.isPresent()) {
            throw BadRequestException.userAlreadyExistsException(
                    organizerRegistrationRequest.getEmail());
        }
        var userWithToken =
                UserWithToken.builder()
                        .name(organizerRegistrationRequest.getName())
                        .telephone(organizerRegistrationRequest.getTelephone())
                        .email(organizerRegistrationRequest.getEmail())
                        .password(
                                passwordEncoder.encode(organizerRegistrationRequest.getPassword()))
                        .token(UUID.randomUUID().toString())
                        .role(appConfig.getOrganizerRole())
                        .build();
        userWithTokenRepository.save(userWithToken);

        try {
            emailService.sendVerificationEmail(
                    organizerRegistrationRequest.getEmail(),
                    ORGANIZER_REGISTRATION_ACTIVATION_MAIL,
                    ORGANIZER_REGISTRATION_ACTIVATION_MAIL_SUBJECT);
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

    @Transactional(rollbackFor = Exception.class)
    public UserDTO registerUser(BaseUserRegistrationRequest registerRequest) {
        var optionalUser = userRepository.findByEmail(registerRequest.getEmail());
        if (optionalUser.isPresent()) {
            throw BadRequestException.userAlreadyExistsException(registerRequest.getEmail());
        }
        final String token = UUID.randomUUID().toString();
        var userWithToken =
                UserWithToken.builder()
                        .name(registerRequest.getName())
                        .email(registerRequest.getEmail())
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .token(token)
                        .role(appConfig.getBaseRole())
                        .build();
        userWithTokenRepository.save(userWithToken);

        try {
            emailService.sendVerificationEmail(
                    registerRequest.getEmail(),
                    String.format(
                            BASE_USER_REGISTRATION_ACTIVATION_MAIL,
                            appConfig.getEnvironmentUrl()
                                    + contextPath
                                    + "/base/activate/"
                                    + token),
                    BASE_USER_REGISTRATION_ACTIVATION_MAIL_SUBJECT);
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
}
