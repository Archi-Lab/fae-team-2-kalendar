package de.th.koeln.fae.microservice_kalendar.kalender.controller;

import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Datum;
import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Titel;
import org.springframework.hateoas.ResourceSupport;

/**
 * Klasse zur REST-Darstellungsdefinition eines Kalendereintrags in einer Kalendereintragsliste eines Kalenders
 */
public class KalendereintragResource extends ResourceSupport {
    public Titel titel;
    public Datum datum;
}