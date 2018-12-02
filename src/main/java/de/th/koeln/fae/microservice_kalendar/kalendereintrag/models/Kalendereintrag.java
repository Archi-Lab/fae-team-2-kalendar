package de.th.koeln.fae.microservice_kalendar.kalendereintrag.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Kalendereintrag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Setter
    @Embedded
    private Datum datum;

    @Getter
    @Setter
    @Embedded
    private Ort ort;

    @Getter
    @Setter
    @Embedded
    private Titel titel;

    @Getter
    @Setter
    @Embedded
    private Beschreibung beschreibung;
}
