package ru.job4j.passport.resttemplate.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "passports")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer series;
    @NotNull
    private Long number;
    private LocalDate releaseDate;
    private LocalDate expireDate;
    private String firstname;
    private String lastname;
    private LocalDate birthDate;

    @OneToMany
    private List<Address> registrationAddress;

    @Override
    public String toString() {
        return "Passport{"
                + "id=" + id
                + ", series=" + series
                + ", number=" + number
                + ", releaseDate=" + releaseDate
                + ", firstname='" + firstname + '\''
                + ", lastname='" + lastname + '\''
                + ", registrationAddress='" + registrationAddress + '\''
                + ", addresses=" + registrationAddress
                + '}';
    }
}
