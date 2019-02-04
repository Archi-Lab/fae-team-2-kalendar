package de.th.koeln.fae.microservice_kalendar.kalender.controller;

import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Datum;
import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Titel;
import org.springframework.hateoas.ResourceSupport;

public class KalendereintragResource extends ResourceSupport {
    public Datum datum;
    public Titel titel;
}