package de.th.koeln.fae.microservice_kalendar.kalender.models;

import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Kalendereintrag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Kalender {

    @Id
    private UUID id = UUID.randomUUID();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "kalender_id")
    private List<Kalendereintrag> kalendereintragListe;

    @ManyToOne(cascade = CascadeType.ALL)
    private DVP dvp;

    @Embedded
    private Name name;

    @Embedded
    private Zeitzone zeitzone;

    @Override
    public String toString() {
        return "Kalender{" +
                "name='" + name + '\'' +
                ", zeitzone='" + zeitzone.toString() +
                '}';
    }

    public List<Kalendereintrag> getKalendereintragListe() {
        return kalendereintragListe;
    }

    public void setKalendereintragListe(List<Kalendereintrag> kalendereintragListe) {
        this.kalendereintragListe = kalendereintragListe;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Zeitzone getZeitzone() {
        return zeitzone;
    }

    public void setZeitzone(Zeitzone zeitzone) {
        this.zeitzone = zeitzone;
    }

    public DVP getDvp() {
        return dvp;
    }

    public void setDvp(DVP dvp) {
        this.dvp = dvp;
    }
}
