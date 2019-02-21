package de.th.koeln.fae.microservice_kalendar.kalender.controller;

import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.ArrayList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Klasse zum Befüllen der Kalender-REST-Darstellung {@link KalenderResource} aus einer {@link Kalender} Entität
 */
public class KalenderResourceAssembler extends ResourceAssemblerSupport<Kalender, KalenderResource> {

    public KalenderResourceAssembler() {
        super(KalenderController.class, KalenderResource.class);
    }

    @Override
    public KalenderResource toResource(Kalender kalender) {
        KalenderResource resource = instantiateResource(kalender);

        resource.name = kalender.getName();
        resource.dvp = (new DVPResourceAssembler()).toResource(kalender.getDvp());
        resource.zeitzone = kalender.getZeitzone();

        KalendereintragResourceAssembler kalendereintragResourceAssembler = new KalendereintragResourceAssembler();

        resource.kalendereintragListe = new ArrayList<>(kalender.getKalendereintragListe().size());
        for (var kalendereintrag : kalender.getKalendereintragListe()) {
            resource.kalendereintragListe.add(kalendereintragResourceAssembler.toResource(kalendereintrag));
        }

//        resource.add(linkTo(methodOn(KalenderController.class).getKalender(kalender.getId())).withSelfRel());
        return resource;
    }
}
