package de.th.koeln.fae.microservice_kalendar;


import de.th.koeln.fae.microservice_kalendar.infrastructure.KafkaGateway;
import de.th.koeln.fae.microservice_kalendar.infrastructure.KalenderCreatedEvent;
import de.th.koeln.fae.microservice_kalendar.infrastructure.KalenderEvent;
import de.th.koeln.fae.microservice_kalendar.kalender.models.DVP;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Name;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Zeitzone;
import de.th.koeln.fae.microservice_kalendar.kalender.repositories.KalenderRepository;
import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.*;
import de.th.koeln.fae.microservice_kalendar.kalendereintrag.repositories.KalendereintragRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Component
public class SampleDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private KalenderRepository kalenderRepository;

    @Autowired
    private KalendereintragRepository kalendereintragRepository;

    private final KafkaGateway eventPublisher;
    private static final Logger log = LoggerFactory.getLogger(SampleDataLoader.class);
    @Autowired
    SampleDataLoader(final KafkaGateway eventPublisher){
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        final Kalender kalender = new Kalender();
        final Kalendereintrag kalendereintrag = new Kalendereintrag();
        final DVP dvp = new DVP();

        List<Kalendereintrag> kalendereintragListe = new ArrayList<Kalendereintrag>();
        kalendereintragListe.add(kalendereintrag);

        List<Kalender> kalenderList = new ArrayList<Kalender>();
        kalenderList.add(kalender);

        //fülle Kalender
        Name name = new Name("Artztermine");
        Zeitzone zeitzone = new Zeitzone(TimeZone.getTimeZone("CET"));

        kalender.setName(name);
        kalender.setZeitzone(zeitzone);
        kalender.setKalendereintragListe(kalendereintragListe);

        kalender.setDvp(dvp);
        dvp.setKalender(kalenderList);

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

        /**KalenderEvent kalenderEvent = new KalenderCreatedEvent(kalender);
        try {
            SendResult<String, String> sendResult = eventPublisher.publishTrackingEvent(kalenderEvent)
                    .get(1, TimeUnit.SECONDS);
            log.info(sendResult.toString());
        } catch (final Exception e){
            log.info("An " + e.getClass() + " occured!");
        }**/

        //speichere Kalender und Kalendereintrag
        final Kalender savedKalender = this.kalenderRepository.save(kalender);
        final Kalendereintrag savedKalendereintrag = this.kalendereintragRepository.save(kalendereintrag);

    }
}
