package ru.job4j.passport.resttemplate.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.job4j.passport.resttemplate.model.Passport;

import java.util.List;

@Repository
public class PassportRepository {

    @Autowired
    private RestTemplate rest;

    private static final String API = "http://localhost:8080/passport/";

    public List<Passport> findAll() {
        return rest.exchange(
                API + "/find",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
    }

    public List<Passport> findBySeries(String series) {
        return rest.exchange(
                API + "/find?series=" + series,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
    }

    public Passport save(Passport passport) {
        return rest.postForObject(API + "/save", passport, Passport.class);
    }

    public void update(Passport passport, String id) {
        rest.exchange(API + "/update/" + id, HttpMethod.PUT, new HttpEntity<>(passport), Void.class);
    }

    public void delete(String id) {
        rest.delete(API + "/delete/" + id);
    }

    public List<Passport> findUnavailable() {
        return rest.exchange(
                API + "/unavailable",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
    }

    public List<Passport> findReplaceable() {
        return rest.exchange(
                API + "/find-replaceable",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
    }
}
