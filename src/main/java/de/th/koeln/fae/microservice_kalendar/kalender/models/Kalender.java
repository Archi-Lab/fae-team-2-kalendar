package de.th.koeln.fae.microservice_kalendar.kalender.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.produce.EventPublishingEntityListener;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.produce.EventSource;
import de.th.koeln.fae.microservice_kalendar.kalender.models.DVP.DVP;
import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Kalendereintrag;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(EventPublishingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Kalender extends EntityUUID4 implements EventSource{

    @Version
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "kalender_id")
    private List<Kalendereintrag> kalendereintragListe;

    @ManyToOne
    private DVP dvp;

    @Embedded
    private Name name;

    @Embedded
    private Zeitzone zeitzone;

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

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    public String getAggregateName() {
        return "kalender";
    }

    @Override
    public String toString() {
        return "Kalender{" +
                "name='" + name + '\'' +
                ", zeitzone='" + zeitzone.toString() +
                '}';
    }
}
