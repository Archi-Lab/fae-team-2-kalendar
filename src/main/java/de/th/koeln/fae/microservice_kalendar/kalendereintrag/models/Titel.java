package de.th.koeln.fae.microservice_kalendar.kalendereintrag.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
public class Titel {

    private String titel;

    public Titel(){

    }

    public Titel(String titel){

        this.titel = titel;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }
}
