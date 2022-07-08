package ru.job4j.passport.resttemplate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
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

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    private final PassportService passportService;

    @Scheduled(fixedDelay = 10000, initialDelay = 100)
    public void checkReplaceablePassports() {
        List<Passport> passports = passportService.findReplaceable();
        if (!passports.isEmpty()) {
            sendMessageToKafkaPassportsTopic(passports);
        }
    }

    public void sendMessageToKafkaPassportsTopic(List<Passport> replaceablePassports) {
        replaceablePassports.forEach(passport -> kafkaTemplate.send("replacePassports",
                "Passport "
                        + passport.getSeries()
                        + " "
                        + passport.getNumber()
                        + " needs to be replaced."
        ));
    }


}


