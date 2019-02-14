package de.th.koeln.fae.microservice_kalendar.kalender.models;

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
    private List<Kalender> kalender;

    public List<Kalender> getKalender() {
        return kalender;
    }

    public void setKalender(List<Kalender> kalender) {
        this.kalender = kalender;
    }
}
