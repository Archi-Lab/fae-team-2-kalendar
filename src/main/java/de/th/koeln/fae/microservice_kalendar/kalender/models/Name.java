package de.th.koeln.fae.microservice_kalendar.kalender.models;

import javax.persistence.Embeddable;

/*
Attribut Name der Kalender Entität. Wird durch diese Klasse explizit gemacht.
 */
@Embeddable
public class Name {

    private String name;

    //region Konstruktoren
    public Name(){

    }

    public Name(String name){

        //Verwendung eines Regex, um nur Namen bestehend aus Groß- bzw. Kleinbuchstaben zu akzeptieren
        String expression = "^[a-zA-Z\\s]+";
        if(!name.matches(expression)){
            throw new IllegalArgumentException("Ein Kalendername darf nur aus Groß- bzw. Kleinbuchstaben bestehen.");
        }
        this.name = name;

    }
    //endregion

    //region Getter,Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion
}
