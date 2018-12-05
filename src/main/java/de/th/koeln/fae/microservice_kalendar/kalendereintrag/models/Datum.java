package de.th.koeln.fae.microservice_kalendar.kalendereintrag.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Calendar;

@Embeddable
public class Datum {

    private Calendar datum;

    public Datum(){

    }

    public Datum (Calendar datum){
        this.datum = datum;
    }

    public Calendar getDatum() {
        return datum;
    }

    public void setDatum(Calendar datum) {
        this.datum = datum;
    }
}
