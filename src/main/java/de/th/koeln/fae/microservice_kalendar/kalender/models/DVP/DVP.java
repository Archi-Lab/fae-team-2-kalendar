package de.th.koeln.fae.microservice_kalendar.kalender.models.DVP;

import com.fasterxml.jackson.annotation.JsonBackReference;
import de.th.koeln.fae.microservice_kalendar.kalender.models.EntityUUID4;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;

import javax.persistence.*;
import java.util.List;

/*
DVP Entität, die für die Zuordnung von Kalender zu DVP gehalten wird.
Repliziert Teilelemente des DVP MS, um entsprechende Informationen aus dem DVP-Topic zu erhalten
und in diesem MS zu verwenden.
 */
@Entity
public class DVP extends EntityUUID4 {

    public DVP() {
    }

    //region Attribute
    //Darstellung der 1:N-Beziehung zwischen dem Kalender(1) und der/den DVPs(N)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "dvp_id")
    @JsonBackReference
    private List<Kalender> kalender;

    @Embedded
    private Vorname vorname;

    @Embedded
    private Nachname nachname;
    //endregion

    //region Getter,Setter
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
    //endregion
}
