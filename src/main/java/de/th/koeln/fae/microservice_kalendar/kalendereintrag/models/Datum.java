package de.th.koeln.fae.microservice_kalendar.kalendereintrag.models;

import javax.persistence.Embeddable;
import java.util.Calendar;

/**
Attribut Datum der Kalendereintrag Entität. Wird durch diese Klasse explizit gemacht.
 */
@Embeddable
public class Datum {

    private Calendar datum;

    //region Konstruktoren
    public Datum(){

    }

    public Datum (Calendar datum){
        this.datum = datum;
    }
    //endregion

    //region Getter,Setter
    public Calendar getDatum() {
        return datum;
    }

    public void setDatum(Calendar datum) {
        this.datum = datum;
    }
    //endregion
}
