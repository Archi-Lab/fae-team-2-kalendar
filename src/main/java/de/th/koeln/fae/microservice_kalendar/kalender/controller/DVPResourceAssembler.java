package de.th.koeln.fae.microservice_kalendar.kalender.controller;

import de.th.koeln.fae.microservice_kalendar.kalender.models.DVP.DVP;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class DVPResourceAssembler extends ResourceAssemblerSupport<DVP, DVPResource> {

    public DVPResourceAssembler() {
        super(KalenderController.class, DVPResource.class);
    }

    @Override
    public DVPResource toResource(DVP dvp) {
        DVPResource resource = instantiateResource(dvp);
        //TODO: how to create links over ms bounds
        resource.add(new Link("/dvps/{dvpId}").expand(dvp.getId()));
        return resource;
    }
}