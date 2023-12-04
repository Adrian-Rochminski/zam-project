/* (C)2023 */
package com.studies.zamproject.configuration;

import com.studies.zamproject.configuration.config.AppConfig;
import com.studies.zamproject.entities.Event;
import com.studies.zamproject.entities.Tag;
import com.studies.zamproject.entities.User;
import com.studies.zamproject.repositories.EventRepository;
import com.studies.zamproject.repositories.TagRepository;
import com.studies.zamproject.repositories.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StartupConfig {

    public static final String TEST = "test@test.com";
    public static final String ORGANIZER = "organizer@test.com";
    public static final String ORGANIZER2 = "organizer2@test.com";
    public static final String BASE = "base@test.com";
    public static final int EVENT_COUNT_ON_STARTUP = 7;
    private final AppConfig appConfig;

    @Bean
    public CommandLineRunner initData(
            UserRepository userRepository,
            TagRepository tagRepository,
            EventRepository eventRepository) {
        return (args) -> {
            if (!userRepository.existsByEmail(TEST)) {
                userRepository.save(
                        User.builder()
                                .email(TEST)
                                .name("test")
                                .password(
                                        "$2a$12$1ZdVtupqu72K7kUDOu4uVumukogroUEDJYVyihQgJdTAP7uNSFEoK")
                                .role(appConfig.getAdminRole())
                                .telephone("123456789")
                                .build());
            }
            if (!userRepository.existsByEmail(ORGANIZER)) {
                userRepository.save(
                        User.builder()
                                .email(ORGANIZER)
                                .name("test")
                                .password(
                                        "$2a$12$1ZdVtupqu72K7kUDOu4uVumukogroUEDJYVyihQgJdTAP7uNSFEoK")
                                .role(appConfig.getOrganizerRole())
                                .telephone("123456789")
                                .build());
            }
            if (!userRepository.existsByEmail(BASE)) {
                userRepository.save(
                        User.builder()
                                .email(BASE)
                                .name("test")
                                .password(
                                        "$2a$12$1ZdVtupqu72K7kUDOu4uVumukogroUEDJYVyihQgJdTAP7uNSFEoK")
                                .role(appConfig.getBaseRole())
                                .telephone("123456789")
                                .build());
            }
            if (!userRepository.existsByEmail(ORGANIZER2)) {
                userRepository.save(
                        User.builder()
                                .email(ORGANIZER2)
                                .name("test")
                                .password(
                                        "$2a$12$1ZdVtupqu72K7kUDOu4uVumukogroUEDJYVyihQgJdTAP7uNSFEoK")
                                .role(appConfig.getBaseRole())
                                .telephone("123456789")
                                .build());
            }
            if (tagRepository.count() < appConfig.getTags().size()) {
                appConfig
                        .getTags()
                        .forEach(
                                tagName -> tagRepository.save(Tag.builder().name(tagName).build()));
            }
            if (eventRepository.count() < EVENT_COUNT_ON_STARTUP) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                eventRepository.save(
                        Event.builder()
                                .free(false)
                                .latitude(51.7592)
                                .longitude(19.4560)
                                .startTime(LocalDateTime.parse("2023-12-03 14:00:00", formatter))
                                .endTime(LocalDateTime.parse("2023-12-03 22:00:00", formatter))
                                .owner(userRepository.findByEmail(TEST).get())
                                .name("Łódź Music Festival")
                                .description(
                                        "Join us for a day of music and fun at the annual Łódź"
                                            + " Music Festival featuring local and international"
                                            + " artists.")
                                .build());
                eventRepository.save(
                        Event.builder()
                                .free(true)
                                .latitude(51.6649)
                                .longitude(19.3549)
                                .startTime(LocalDateTime.parse("2023-12-05 20:00:00", formatter))
                                .endTime(LocalDateTime.parse("2023-12-05 23:00:00", formatter))
                                .owner(userRepository.findByEmail(TEST).get())
                                .name("Open-Air Cinema Night")
                                .description(
                                        "Enjoy a classic film under the stars at our outdoor cinema"
                                                + " event near Pabianice.")
                                .build());
                eventRepository.save(
                        Event.builder()
                                .free(true)
                                .latitude(51.7500)
                                .longitude(19.3333)
                                .startTime(LocalDateTime.parse("2023-12-10 11:00:00", formatter))
                                .endTime(LocalDateTime.parse("2023-12-10 17:00:00", formatter))
                                .owner(userRepository.findByEmail(TEST).get())
                                .name("Konstantynów Łódzki Food Fair")
                                .description(
                                        "Explore local and international cuisine at our annual food"
                                                + " fair in Konstantynów Łódzki.")
                                .build());
                eventRepository.save(
                        Event.builder()
                                .free(false)
                                .latitude(51.8193)
                                .longitude(19.3033)
                                .startTime(LocalDateTime.parse("2023-12-15 10:00:00", formatter))
                                .endTime(LocalDateTime.parse("2023-12-15 16:00:00", formatter))
                                .owner(userRepository.findByEmail(TEST).get())
                                .name("Creative Art Workshop")
                                .description(
                                        "Discover your inner artist at our creative art workshop"
                                            + " near Aleksandrów Łódzki. All materials provided.")
                                .build());
                eventRepository.save(
                        Event.builder()
                                .free(true)
                                .latitude(51.7738889)
                                .longitude(19.4594444)
                                .startTime(LocalDateTime.parse("2023-11-18 12:00:00", formatter))
                                .endTime(LocalDateTime.parse("2023-11-18 18:00:00", formatter))
                                .owner(userRepository.findByEmail(TEST).get())
                                .name("Wystawa \"Chwile ulotne chwytam\"")
                                .description(
                                        "Dorota Masłowska, Wojna polsko-ruska pod flagą"
                                            + " biało-czerwoną Kuchennymi drzwiami, mieszając w"
                                            + " kotle kreatywności, cierpliwie i z uważnością Ola"
                                            + " Ignasiak i Karolina Gębka z kolektywu Slow Painting"
                                            + " Studio znalazły swoją artystyczną niszę. Ich"
                                            + " malarstwo bazujące na ręcznie pozyskiwanych"
                                            + " barwnikach z resztek roślin jadalnych jest"
                                            + " odpowiedzią na globalne wyzwanie klimatyczne, wobec"
                                            + " którego świat sztuki nie może pozostać obojętny.")
                                .build());
                eventRepository.save(
                        Event.builder()
                                .free(false)
                                .latitude(51.7733333)
                                .longitude(19.469722)
                                .startTime(LocalDateTime.parse("2023-12-03 17:00:00", formatter))
                                .endTime(LocalDateTime.parse("2023-12-03 21:00:00", formatter))
                                .owner(userRepository.findByEmail(TEST).get())
                                .name("Lamaila w Teatrze Wielkim")
                                .description(
                                        "Widowisko taneczne w dwóch aktach i 19 scenach. Spektakl"
                                            + " familijny. Historia mówi o wydarzeniach dziejących"
                                            + " się w Krainie Dnia i Nocy, gdzie główna bohaterka,"
                                            + " Księżniczka Dnia – tytułowa Lamaila – zostaje nagle"
                                            + " w środku nocy obudzona przez śmiesznego stwora"
                                            + " mieszkającego w głębinach bagna – Mr. Gulgacza.")
                                .build());
                eventRepository.save(
                        Event.builder()
                                .free(false)
                                .latitude(51.77)
                                .longitude(19.565555)
                                .startTime(LocalDateTime.parse("2023-12-03 10:00:00", formatter))
                                .endTime(LocalDateTime.parse("2023-12-03 17:00:00", formatter))
                                .owner(userRepository.findByEmail(TEST).get())
                                .name("Mikołajkowy turniej tenisowy")
                                .description(
                                        "Najmłodsi adepci tenisa ziemnego wystartują w objętym"
                                                + " honorowym patronatem Prezydenta Miasta Łodzi"
                                                + " Turnieju Mikołajkowym")
                                .build());
            }
        };
    }
}
