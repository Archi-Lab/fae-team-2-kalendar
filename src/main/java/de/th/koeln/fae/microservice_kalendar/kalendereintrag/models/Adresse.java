package de.th.koeln.fae.microservice_kalendar.kalendereintrag.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
public class Adresse {

    private String strasse;

    private String hausnummer;

    private String ort;

    private String plz;

    public Adresse() {

    }

    public Adresse(String strasse, String hausnummer, String ort, String plz) {

        String expressionGrossKlein = "^[a-zA-Z\\s]+";
        if (!strasse.matches(expressionGrossKlein)) {
            throw new IllegalArgumentException("Ein Straßenname darf nur aus Groß- bzw. Kleinbuchstaben bestehen.");
        }
        this.strasse = strasse;

        /**String expressionHausnummer = "\b[0-9]{1,3}[a-dA-D]{0,1}\b";
         if(!strasse.matches(expressionHausnummer)){
         throw new IllegalArgumentException("Die angegebene Hausnummer ist nicht korrekt.");
         }**/
        this.hausnummer = hausnummer;

        if (!ort.matches(expressionGrossKlein)) {
            throw new IllegalArgumentException("Ein Ortsname darf nur aus Groß- bzw. Kleinbuchstaben bestehen.");
        }
        this.ort = ort;

        /**String expressionPLZ = "\b[0-9]{1,5}\b";
         if(!plz.matches(expressionPLZ)){
         throw new IllegalArgumentException("Die angegebene Postleitzahl ist nicht korrekt.");
         }**/
        this.plz = plz;
    }

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
}
