package de.th.koeln.fae.microservice_kalendar.kalender.controller;

import de.th.koeln.fae.microservice_kalendar.kalendereintrag.controller.KalendereintragController;
import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Kalendereintrag;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


public class KalendereintragResourceAssembler extends ResourceAssemblerSupport<Kalendereintrag, KalendereintragResource> {

    public KalendereintragResourceAssembler() {
        super(KalendereintragController.class, KalendereintragResource.class);
    }

    @Override
    public KalendereintragResource toResource(Kalendereintrag kalendereintrag) {

        KalendereintragResource resource = instantiateResource(kalendereintrag);

        resource.datum = kalendereintrag.getDatum();
        resource.titel = kalendereintrag.getTitel();

        resource.add(linkTo(methodOn(KalendereintragController.class)
                            .getKalendereintrag(
                                    kalendereintrag.getKalender().getId(),
                                    kalendereintrag.getId()))
                    .withSelfRel());

        return resource;
    }
}