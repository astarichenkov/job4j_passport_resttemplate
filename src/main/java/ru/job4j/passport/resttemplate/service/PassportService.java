package ru.job4j.passport.resttemplate.service;

import org.springframework.stereotype.Service;
import ru.job4j.passport.resttemplate.model.Passport;
import ru.job4j.passport.resttemplate.repository.PassportRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PassportService {

    private final PassportRepository passports;

    public PassportService(PassportRepository passports) {
        this.passports = passports;
    }

    public List<Passport> findAll() {
        List<Passport> rsl = new ArrayList<>();
        passports.findAll().forEach(rsl::add);
        return rsl;
    }

    public Passport save(Passport passport) {
        return passports.save(passport);
    }

    public void update(Passport passport, String id) {
        passports.update(passport, id);
    }

    public void delete(String id) {
        passports.delete(id);
    }

    public List<Passport> findBySeries(String series) {
        return passports.findBySeries(series);
    }

    public List<Passport> findUnavailable() {
        return passports.findUnavailable();
    }

    public List<Passport> findReplaceable() {
        return passports.findReplaceable();
    }
}
