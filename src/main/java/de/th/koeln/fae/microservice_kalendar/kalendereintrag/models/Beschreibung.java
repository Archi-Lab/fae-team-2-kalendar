package de.th.koeln.fae.microservice_kalendar.kalendereintrag.models;

import javax.persistence.Embeddable;

/**
Attribut Beschreibung der Kalendereintrag Entität sowie Value-Object im Kalendereintrag-Aggregat.
Wird durch diese Klasse explizit gemacht.
 */
@Embeddable
public class Beschreibung {

    private String beschreibung;

    //region Konstruktoren
    public Beschreibung(){}

    public Beschreibung(String beschreibung){

        //Nur Beschreibungen mit weniger als 200 Zeichen akzeptieren.
        if(beschreibung.length() > 200){
            throw new IllegalArgumentException("Die Beschreibung darf nur aus 200 Zeichen bestehen.");
        }
        this.beschreibung = beschreibung;
    }
    //endregion

    //region Getter,Setter
    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    //endregion
}
