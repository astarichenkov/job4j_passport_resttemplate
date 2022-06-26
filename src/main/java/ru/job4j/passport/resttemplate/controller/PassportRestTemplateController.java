package ru.job4j.passport.resttemplate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.passport.resttemplate.model.Passport;
import ru.job4j.passport.resttemplate.service.PassportService;

import java.util.List;

@RestController
@RequestMapping("/passport")
@RequiredArgsConstructor
public class PassportRestTemplateController {
    @Autowired
    private RestTemplate rest;

    private static final String API = "http://localhost:8080/passport/";

    private final PassportService passports;

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Passport>> find(@RequestParam(name = "series", required = false) String series) {
        List<Passport> passportList;
        if (series == null) {
            passportList = passports.findAll();
        } else {
            passportList = passports.findBySeries(series);
        }
        return new ResponseEntity<>(passportList, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Passport> save(@RequestBody Passport passport) {
        Passport rsl = passports.save(passport);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@RequestBody Passport passport, @PathVariable String id) {
        try {
            passports.update(passport, id);
            return new ResponseEntity<>(
                    HttpStatus.OK
            );
        } catch (Throwable e) {
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            passports.delete(id);
            return new ResponseEntity<>(
                    HttpStatus.OK
            );
        } catch (Throwable e) {
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/unavailable")
    public ResponseEntity<List<Passport>> findExpiredPassports() {

        return new ResponseEntity<>(passports.findUnavailable(), HttpStatus.OK);
    }

    @GetMapping("/find-replaceable")
    public ResponseEntity<List<Passport>> findReplaceablePassports() {
        return new ResponseEntity<>(passports.findReplaceable(), HttpStatus.OK);
    }

}

