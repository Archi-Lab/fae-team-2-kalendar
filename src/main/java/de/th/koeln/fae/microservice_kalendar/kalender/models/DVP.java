package de.th.koeln.fae.microservice_kalendar.kalender.models;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class DVP {

    public DVP() {
    }

    @Id
    private UUID id = UUID.randomUUID();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    @OneToMany
    @JoinColumn(name = "dvp_id")
    private List<Kalender> kalender;

    public List<Kalender> getKalender() {
        return kalender;
    }

    public void setKalender(List<Kalender> kalender) {
        this.kalender = kalender;
    }
}
