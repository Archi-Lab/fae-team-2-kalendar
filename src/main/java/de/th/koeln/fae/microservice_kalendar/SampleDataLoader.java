package de.th.koeln.fae.microservice_kalendar;


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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

@Component
public class SampleDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private KalenderRepository kalenderRepository;

    @Autowired
    private KalendereintragRepository kalendereintragRepository;

    @Autowired
    private DVPRepository dvpRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        final Kalender kalender = new Kalender();
        final DVP dvp = new DVP();
        dvp.setVorname(new Vorname("Max"));
        dvp.setNachname(new Nachname("Mustermann"));
        final DVP savedDVP = this.dvpRepository.save(dvp);

        final Kalendereintrag kalendereintrag = new Kalendereintrag();

        //fülle Kalendereintrag
        Beschreibung beschreibung = new Beschreibung("Ein neues Gebiss wird benötigt.");
        Datum datum = new Datum(new GregorianCalendar(2019,02,10, 10, 20));
        Adresse adresse = new Adresse("Musterstrasse", "11", "Musterhausen", "51674");
        Titel titel = new Titel("Zahnarzt");

        kalendereintrag.setBeschreibung(beschreibung);
        kalendereintrag.setDatum(datum);
        kalendereintrag.setAdresse(adresse);
        kalendereintrag.setTitel(titel);
        kalendereintrag.setKalender(kalender);
//        final Kalendereintrag savedKalendereintrag = this.kalendereintragRepository.save(kalendereintrag);

        List<Kalendereintrag> kalendereintragListe = new ArrayList<Kalendereintrag>();
        kalendereintragListe.add(kalendereintrag);

        //fülle Kalender
        Name name = new Name("Artztermine");
        Zeitzone zeitzone = new Zeitzone(TimeZone.getTimeZone("CET"));

        kalender.setName(name);
        kalender.setZeitzone(zeitzone);
        kalender.setKalendereintragListe(kalendereintragListe);

        kalender.setDvp(dvp);

        //speichere Kalender und Kalendereintrag
        final Kalender savedKalender = this.kalenderRepository.save(kalender);

    }
}
