package de.th.koeln.fae.microservice_kalendar;


import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Name;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Zeitzone;
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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        final Kalender kalender = new Kalender();
        final Kalendereintrag kalendereintrag = new Kalendereintrag();

        List<Kalendereintrag> kalendereintragListe = new ArrayList<Kalendereintrag>();
        kalendereintragListe.add(kalendereintrag);

        List<Kalender> kalenderListe = new ArrayList<Kalender>();
        kalenderListe.add(kalender);

        //fülle Kalender
        Name name = new Name("Artztermine");
        Zeitzone zeitzone = new Zeitzone(TimeZone.getTimeZone("CET"));

        kalender.setName(name);
        kalender.setZeitzone(zeitzone);
        kalender.setKalendereintragListe(kalendereintragListe);


        //fülle Kalendereintrag
        Beschreibung beschreibung = new Beschreibung("Ein neues Gebiss wird benötigt.");
        Datum datum = new Datum(new GregorianCalendar(2019,02,10, 10, 20));
        Ort ort = new Ort("Musterstrasse", "11", "Musterhausen", "51674");
        Titel titel = new Titel("Zahnartzt");

        kalendereintrag.setBeschreibung(beschreibung);
        kalendereintrag.setDatum(datum);
        kalendereintrag.setOrt(ort);
        kalendereintrag.setTitel(titel);
        kalendereintrag.setKalenderListe(kalenderListe);

        //speichere Kalender und Kalendereintrag
        final Kalender savedKalender = this.kalenderRepository.save(kalender);
        final Kalendereintrag savedKalendereintrag = this.kalendereintragRepository.save(kalendereintrag);

    }
}
