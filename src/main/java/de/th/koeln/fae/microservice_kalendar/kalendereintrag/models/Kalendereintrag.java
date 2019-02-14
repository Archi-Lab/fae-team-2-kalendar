package de.th.koeln.fae.microservice_kalendar.kalendereintrag.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import de.th.koeln.fae.microservice_kalendar.kalender.models.EntityUUID4;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;

import javax.persistence.*;

@Entity
public class Kalendereintrag extends EntityUUID4 {

    @Embedded
    private Datum datum;

    @Embedded
    private Adresse adresse;

    @Embedded
    private Titel titel;

    @Embedded
    private Beschreibung beschreibung;

    @ManyToOne
    @JsonBackReference
    private Kalender kalender;

    public Kalender getKalender() {
        return kalender;
    }

    public void setKalender(Kalender kalender) {
        this.kalender = kalender;
    }

    public Datum getDatum() {
        return datum;
    }

    public void setDatum(Datum datum) {
        this.datum = datum;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Titel getTitel() {
        return titel;
    }

    public void setTitel(Titel titel) {
        this.titel = titel;
    }

    public Beschreibung getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(Beschreibung beschreibung) {
        this.beschreibung = beschreibung;
    }
}
