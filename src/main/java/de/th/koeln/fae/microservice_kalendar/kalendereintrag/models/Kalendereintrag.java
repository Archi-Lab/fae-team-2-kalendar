package de.th.koeln.fae.microservice_kalendar.kalendereintrag.models;

import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Kalendereintrag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToMany
    private List<Kalender> kalenderListe;

    @Embedded
    private Datum datum;

    @Embedded
    private Ort ort;

    @Embedded
    private Titel titel;

    @Embedded
    private Beschreibung beschreibung;


    public List<Kalender> getKalenderListe() {
        return kalenderListe;
    }

    public void setKalenderListe(List<Kalender> kalenderListe) {
        this.kalenderListe = kalenderListe;
    }

    public Datum getDatum() {
        return datum;
    }

    public void setDatum(Datum datum) {
        this.datum = datum;
    }

    public Ort getOrt() {
        return ort;
    }

    public void setOrt(Ort ort) {
        this.ort = ort;
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
