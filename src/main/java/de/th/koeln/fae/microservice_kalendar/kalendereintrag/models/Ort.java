package de.th.koeln.fae.microservice_kalendar.kalendereintrag.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
public class Ort {

    @Getter
    @Setter
    private String strasse;

    @Getter
    @Setter
    private String hausnummer;

    @Getter
    @Setter
    private String ort;

    @Getter
    @Setter
    private String plz;

    public Ort() {

    }

    public Ort(String strasse, String hausnummer, String ort, String plz) {

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

}
