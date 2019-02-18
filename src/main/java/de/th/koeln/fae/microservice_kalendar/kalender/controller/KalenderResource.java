package de.th.koeln.fae.microservice_kalendar.kalender.controller;

import de.th.koeln.fae.microservice_kalendar.kalender.models.Name;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Zeitzone;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

/**
 * Klasse zur REST-Darstellungsdefinition eines Kalenders
 */
public class KalenderResource extends ResourceSupport {
    public Name name;
    public DVPResource dvp;
    public Zeitzone zeitzone;
    public List<KalendereintragResource> kalendereintragListe;
}

