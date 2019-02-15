package de.th.koeln.fae.microservice_kalendar.kalender.models.DVP;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.th.koeln.fae.microservice_kalendar.kalender.models.EntityUUID4;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class DVP extends EntityUUID4 {

    public DVP() {
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "dvp_id")
    @JsonBackReference
    private List<Kalender> kalender;

    @Embedded
    private Vorname vorname;

    @Embedded
    private Nachname nachname;

    public List<Kalender> getKalender() {
        return kalender;
    }

    public void setKalender(List<Kalender> kalender) {
        this.kalender = kalender;
    }

    public Vorname getVorname() {
        return vorname;
    }

    public void setVorname(Vorname vorname) {
        this.vorname = vorname;
    }

    public Nachname getNachname() {
        return nachname;
    }

    public void setNachname(Nachname nachname) {
        this.nachname = nachname;
    }
}
