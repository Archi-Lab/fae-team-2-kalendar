package de.th.koeln.fae.microservice_kalendar.kalendereintrag.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
public class Beschreibung {

    @Getter
    @Setter
    private String beschreibung;

    public Beschreibung(){}

    public Beschreibung(String beschreibung){
        if(beschreibung.length() > 200){
            throw new IllegalArgumentException("Die Beschreibung darf nur aus 200 Zeichen bestehen.");
        }
        this.beschreibung = beschreibung;
    }
}
