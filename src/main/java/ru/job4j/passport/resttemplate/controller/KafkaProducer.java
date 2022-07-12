package ru.job4j.passport.resttemplate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.job4j.passport.resttemplate.model.Passport;
import ru.job4j.passport.resttemplate.service.PassportService;

import java.util.List;

@EnableKafka
@EnableScheduling
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final PassportService passportService;

    @Scheduled(fixedDelay = 10000, initialDelay = 100)
    public void checkReplaceablePassports() {
        List<Passport> passports = passportService.findReplaceable();
        if (!passports.isEmpty()) {
            passportService.sendMessageToKafkaPassportsTopic(passports);
        }
    }

}


