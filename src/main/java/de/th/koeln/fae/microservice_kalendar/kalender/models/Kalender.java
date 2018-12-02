package de.th.koeln.fae.microservice_kalendar.kalender.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Kalender {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Embedded
    @Getter
    @Setter
    private Name name;

    @Embedded
    @Getter
    @Setter
    private Zeitzone zeitzone;

    @Override
    public String toString() {
        return "Kalender{" +
                "name='" + name + '\'' +
                ", zeitzone='" + zeitzone.toString() +
                '}';
    }

}
