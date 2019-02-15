package de.th.koeln.fae.microservice_kalendar.kalender.models.DVP;

import javax.persistence.Embeddable;

/**
Attribut Vorname der DVP Entität sowie Value-Object im Kalender-Aggregat. Wird durch diese Klasse explizit gemacht.
 */
@Embeddable
public class Vorname {

    private String vorname;

    //region Konstruktoren
    public Vorname(){

    }

    public Vorname(String vorname){

        //Verwendung eines Regex, um nur Vornamen bestehend aus Groß- bzw. Kleinbuchstaben zu akzeptieren
        String expression = "^[a-zA-Z\\s]+";
        if(!vorname.matches(expression)){
            throw new IllegalArgumentException("Ein Vorname darf nur aus Groß- bzw. Kleinbuchstaben bestehen.");
        }
        this.vorname = vorname;
    }
    //endregion

    //region Getter,Setter
    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }
    //endregion

    //region Override-Methoden
    @Override
    public String toString() {
        return "Vorname{" +
                "vorname='" + vorname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object other){
        return other.getClass() == this.getClass() && ((Vorname) other).vorname.equals(this.vorname);
    }
    //endregion
}
