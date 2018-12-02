package de.th.koeln.fae.microservice_kalendar;


import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Name;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Zeitzone;
import de.th.koeln.fae.microservice_kalendar.kalender.repositories.KalenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
public class SampleDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private KalenderRepository kalenderRepository;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        final Kalender kalender = new Kalender();

        Name name = new Name("Artztermine");
        Zeitzone zeitzone = new Zeitzone(TimeZone.getTimeZone("CET"));

        kalender.setName(name);
        kalender.setZeitzone(zeitzone);

        final Kalender savedKalender = this.kalenderRepository.save(kalender);

    }
}
