package de.th.koeln.fae.microservice_kalendar.kalender.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.TimeZone;

@Embeddable
public class Zeitzone {

    @Getter
    @Setter
    private TimeZone zeitzone;

    public Zeitzone(){

    }

    public Zeitzone(TimeZone zeitzone){
        this.zeitzone = zeitzone;
    }

}
