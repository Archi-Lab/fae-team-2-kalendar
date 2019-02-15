package de.th.koeln.fae.microservice_kalendar.kalender.models.DVP;

import javax.persistence.Embeddable;

/**
Attribut Nachname der DVP Entität sowie Value-Object im Kalender-Aggregat. Wird durch diese Klasse explizit gemacht.
 */
@Embeddable
public class Nachname {

    private String nachname;

    //region Konstruktoren
    public Nachname(){

    }

    public Nachname(String nachname){

        //Verwendung eines Regex, um nur Nachnamen bestehend aus Groß- bzw. Kleinbuchstaben zu akzeptieren
        String expression = "^[a-zA-Z\\s]+";
        if(!nachname.matches(expression)){
            throw new IllegalArgumentException("Ein Nachname darf nur aus Groß- bzw. Kleinbuchstaben bestehen.");
        }
        this.nachname = nachname;
    }
    //endregion

    //region Getter,Setter
    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
    //endregion

    //region Override-Methoden
    @Override
    public String toString() {
        return "Nachname{" +
                "nachname='" + nachname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object other){
        return other.getClass() == this.getClass() && ((Nachname) other).nachname.equals(this.nachname);
    }
    //endregion
}
