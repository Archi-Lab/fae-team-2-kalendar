package de.th.koeln.fae.microservice_kalendar;


import de.th.koeln.fae.microservice_kalendar.kalender.controller.KalenderController;
import de.th.koeln.fae.microservice_kalendar.kalender.models.DVP.DVP;
import de.th.koeln.fae.microservice_kalendar.kalender.models.DVP.Nachname;
import de.th.koeln.fae.microservice_kalendar.kalender.models.DVP.Vorname;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Name;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Zeitzone;
import de.th.koeln.fae.microservice_kalendar.kalender.repositories.DVPRepository;
import de.th.koeln.fae.microservice_kalendar.kalender.repositories.KalenderRepository;
import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.*;
import de.th.koeln.fae.microservice_kalendar.kalendereintrag.repositories.KalendereintragRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

/**
Wird verwendet, um Beispieldaten zu erzeugen und in der Datenbank zu speichern.
 */
@Component
public class SampleDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final KalenderRepository kalenderRepository;
    private final KalendereintragRepository kalendereintragRepository;
    private final DVPRepository dvpRepository;

    @Autowired
    public SampleDataLoader(KalenderRepository kalenderRepository, KalendereintragRepository kalendereintragRepository, DVPRepository dvpRepository) {
        this.kalenderRepository = kalenderRepository;
        this.kalendereintragRepository = kalendereintragRepository;
        this.dvpRepository = dvpRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        // legt dvp an
        final DVP dvp = new DVP();
        dvp.setId(UUID.fromString("1741a82c-bfa0-497f-8559-a0911e3eccab"));
        dvp.setVorname(new Vorname("Max"));
        dvp.setNachname(new Nachname("Mustermann"));
        final DVP savedDVP = this.dvpRepository.save(dvp);

        final Kalender kalender = new Kalender();
        final Kalendereintrag kalendereintrag = new Kalendereintrag();

        //füllt Kalendereintrag
        Beschreibung beschreibung = new Beschreibung("Ein neues Gebiss wird benötigt.");
        Datum datum = new Datum(new GregorianCalendar(2019, Calendar.FEBRUARY,10, 10, 20));
        Adresse adresse = new Adresse("Musterstrasse", "11", "Musterhausen", "51674");
        Titel titel = new Titel("Zahnarzt");

        kalendereintrag.setBeschreibung(beschreibung);
        kalendereintrag.setDatum(datum);
        kalendereintrag.setAdresse(adresse);
        kalendereintrag.setTitel(titel);
        kalendereintrag.setKalender(kalender);

        List<Kalendereintrag> kalendereintragListe = new ArrayList<>();
        kalendereintragListe.add(kalendereintrag);

        //füllt Kalender
        Name name = new Name("Artztermine");
        Zeitzone zeitzone = new Zeitzone(TimeZone.getTimeZone("CET"));

        kalender.setId(UUID.fromString("f165a802-f962-418d-8367-179db581209e"));
        kalender.setName(name);
        kalender.setZeitzone(zeitzone);
        kalender.setKalendereintragListe(kalendereintragListe);

        kalender.setDvp(dvp);

        //speichert Kalender und Kalendereintrag
        final Kalender savedKalender = this.kalenderRepository.save(kalender);
    }
}
