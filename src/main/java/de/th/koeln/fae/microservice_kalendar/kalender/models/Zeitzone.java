package de.th.koeln.fae.microservice_kalendar.kalender.models;

import javax.persistence.Embeddable;
import java.util.TimeZone;

/**
Attribut Zeitzone der Kalender Entität. Wird durch diese Klasse explizit gemacht.
 */
@Embeddable
public class Zeitzone {

    private TimeZone zeitzone;

    //region Konstruktoren
    public Zeitzone(){

    }

    public Zeitzone(TimeZone zeitzone){
        this.zeitzone = zeitzone;
    }
    //endregion

    //region Getter,Setter
    public TimeZone getZeitzone() {
        return zeitzone;
    }

    public void setZeitzone(TimeZone zeitzone) {
        this.zeitzone = zeitzone;
    }
    //endregion
}
