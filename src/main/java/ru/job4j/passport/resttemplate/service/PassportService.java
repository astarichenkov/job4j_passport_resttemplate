package ru.job4j.passport.resttemplate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.passport.resttemplate.model.Passport;
import ru.job4j.passport.resttemplate.repository.PassportRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PassportService {

    private final PassportRepository passportRepository;

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    public PassportService(PassportRepository passports) {
        this.passportRepository = passports;
    }

    public List<Passport> findAll() {
        List<Passport> rsl = new ArrayList<>();
        passportRepository.findAll().forEach(rsl::add);
        return rsl;
    }

    public Passport save(Passport passport) {
        return passportRepository.save(passport);
    }

    public void update(Passport passport, String id) {
        passportRepository.update(passport, id);
    }

    public void delete(String id) {
        passportRepository.delete(id);
    }

    public List<Passport> findBySeries(String series) {
        return passportRepository.findBySeries(series);
    }

    public List<Passport> findUnavailable() {
        return passportRepository.findUnavailable();
    }

    public List<Passport> findReplaceable() {
        return passportRepository.findReplaceable();
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
