package de.th.koeln.fae.microservice_kalendar.kalender.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.th.koeln.fae.microservice_kalendar.kalender.models.DVP.Nachname;
import de.th.koeln.fae.microservice_kalendar.kalender.models.DVP.Vorname;
import org.springframework.hateoas.ResourceSupport;

import java.util.UUID;

public class DVPResource extends ResourceSupport {
    @JsonProperty
    public UUID id;

    public Vorname vorname;
    public Nachname nachname;
}
