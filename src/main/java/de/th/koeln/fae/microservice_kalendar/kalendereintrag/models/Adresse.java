package de.th.koeln.fae.microservice_kalendar.kalendereintrag.models;

import javax.persistence.Embeddable;

/**
Attribut Adresse der Kalendereintrag Entität. Wird durch diese Klasse explizit gemacht.
 */
@Embeddable
public class Adresse {

    //region Attributes
    private String strasse;

    private String hausnummer;

    private String ort;

    private String plz;
    //endregion

    //region Konstruktoren
    public Adresse() {

    }

    public Adresse(String strasse, String hausnummer, String ort, String plz) {

        //Verwendung eines Regex, um nur Namen bestehend aus Groß- bzw. Kleinbuchstaben zu akzeptieren
        String expressionGrossKlein = "^[a-zA-Z\\s]+";
        if (!strasse.matches(expressionGrossKlein)) {
            throw new IllegalArgumentException("Ein Straßenname darf nur aus Groß- bzw. Kleinbuchstaben bestehen.");
        }
        this.strasse = strasse;

        if (!ort.matches(expressionGrossKlein)) {
            throw new IllegalArgumentException("Ein Ortsname darf nur aus Groß- bzw. Kleinbuchstaben bestehen.");
        }
        this.ort = ort;

        this.hausnummer = hausnummer;
        this.plz = plz;
    }
    //endregion

    //region Getter,Setter
    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }
    //endregion
}
