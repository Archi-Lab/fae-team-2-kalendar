package de.th.koeln.fae.microservice_kalendar.kalender.controller;


import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import de.th.koeln.fae.microservice_kalendar.kalender.repositories.KalenderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
public class KalenderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KalenderController.class);
    private final KalenderRepository kalenderRepository;

    @Autowired
    public KalenderController(KalenderRepository kalenderRepository){
        this.kalenderRepository = kalenderRepository;
    }
    
    
    @GetMapping("/k")
    public ResponseEntity<?> getKalenderList() {
        final Iterable<Kalender> kalenderList = this.kalenderRepository.findAll();
        LOGGER.info("Repo count: " + kalenderRepository.count());

        Resources<Kalender> resources = new Resources<>(kalenderList);

        resources.add(linkTo(methodOn(KalenderController.class).getKalenderList()).withSelfRel());
        
        LOGGER.info("RETURN ALL KALENDERS: " + resources.getContent().size());
        return  ResponseEntity.ok(resources);
    }

    @GetMapping("/k/{kalenderId}")
    public ResponseEntity<?> getKalender(@PathVariable("kalenderId") final Long kalenderId) {
        final Iterable<Kalender> kalenderList = this.kalenderRepository.findAllById(kalenderId);
        
        Resources<Kalender> resources = new Resources<>(kalenderList);

        resources.add(linkTo(methodOn(KalenderController.class).getKalender(kalenderId)).withSelfRel());

        LOGGER.info("RETURN SPECIFIC KALENDER!");
        return  ResponseEntity.ok(resources);
    }

//    private RuntimeException kalenderNotFound(final Long id) {
//        throw new RuntimeException(format("kalender [%s] not found", id));
//    }
}
