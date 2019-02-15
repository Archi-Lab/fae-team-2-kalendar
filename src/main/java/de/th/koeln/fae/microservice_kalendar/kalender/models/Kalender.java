package de.th.koeln.fae.microservice_kalendar.kalender.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.produce.EventPublishingEntityListener;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.produce.EventSource;
import de.th.koeln.fae.microservice_kalendar.kalender.models.DVP.DVP;
import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Kalendereintrag;

import javax.persistence.*;
import java.util.List;

/**
Aggregate-Root des Kalender MS
 */
@Entity
/**
EventPublishingEntityListener.class ist eine generische REWE-Digital Klasse zum erfassen von "created",
"updated" und "deleted" Events.
Triggert das publishen des Kalenders bei einem der oben genannten Aktionen.
 */
@EntityListeners(EventPublishingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Kalender extends EntityUUID4 implements EventSource{

    //region Attribute
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "kalender_id")
    private List<Kalendereintrag> kalendereintragListe;

    //Darstellung der N:1-Beziehung zwischen dem Kalender(N) und der DVP(N)
    @ManyToOne
    private DVP dvp;

    @Version
    private Long version;

    @Embedded
    private Name name;

    @Embedded
    private Zeitzone zeitzone;
    //endregion

    //region Getter,Setter
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
    //endregion

    //region Override-Methoden
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
    //endregion
}
