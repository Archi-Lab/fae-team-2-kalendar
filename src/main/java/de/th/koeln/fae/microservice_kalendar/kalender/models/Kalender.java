package de.th.koeln.fae.microservice_kalendar.kalender.models;

import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Kalendereintrag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Kalender {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "kalender_kalendereintrag",
            joinColumns = @JoinColumn(name = "kalender_id", referencedColumnName = "id"),

            inverseJoinColumns = @JoinColumn(name = "kalendereintrag_id", referencedColumnName = "id"))
    private List<Kalendereintrag> kalendereintragListe;

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
}
