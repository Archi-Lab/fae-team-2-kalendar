package de.th.koeln.fae.microservice_kalendar.kalender.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class DVP {

    public DVP() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany
    @JoinColumn(name = "dvp_id")
    private List<Kalender> kalender;

    public List<Kalender> getKalender() {
        return kalender;
    }

    public void setKalender(List<Kalender> kalender) {
        this.kalender = kalender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
