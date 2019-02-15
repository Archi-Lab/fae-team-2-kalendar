package de.th.koeln.fae.microservice_kalendar.kalendereintrag.models;

import javax.persistence.Embeddable;

/**
Attribut Titel der Kalendereintrag Entität. Wird durch diese Klasse explizit gemacht.
 */
@Embeddable
public class Titel {

    private String titel;

    //region Konstruktoren
    public Titel(){

    }

    public Titel(String titel){

        this.titel = titel;
    }
    //endregion

    //region Getter,Setter
    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }
    //endregion
}
