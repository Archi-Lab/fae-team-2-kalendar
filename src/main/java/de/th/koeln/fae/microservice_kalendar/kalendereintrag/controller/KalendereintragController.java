package de.th.koeln.fae.microservice_kalendar.kalendereintrag.controller;


import de.th.koeln.fae.microservice_kalendar.kalender.controller.KalenderController;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import de.th.koeln.fae.microservice_kalendar.kalender.repositories.KalenderRepository;
import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Kalendereintrag;
import de.th.koeln.fae.microservice_kalendar.kalendereintrag.repositories.KalendereintragRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

import static java.lang.String.format;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
public class KalendereintragController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KalenderController.class);
    private final KalendereintragRepository kalendereintragRepository;
    private final KalenderRepository kalenderRepository;

    @Autowired
    public KalendereintragController(KalendereintragRepository kalendereintragRepository, 
                                     KalenderRepository kalenderRepository){
        this.kalendereintragRepository = kalendereintragRepository;
        this.kalenderRepository = kalenderRepository;
    }
    
    @GetMapping("/k/{kalenderId}/ke")
    public ResponseEntity<?> getKalendereintragList(@PathVariable("kalenderId") final Long kalenderId) { 
        final Iterable<Kalendereintrag> kalendereintragList = this.kalendereintragRepository.findAllByKalender_Id(kalenderId);

        Resources<Kalendereintrag> resources = new Resources<>(kalendereintragList);

        resources.add(linkTo(methodOn(KalendereintragController.class).getKalendereintragList(kalenderId)).withSelfRel());

        LOGGER.info("RETURN ALL KALENDER ENTRIES!");
        return  ResponseEntity.ok(resources);
    }

    @PostMapping("/k/{kalenderId}/ke")
    public ResponseEntity<Kalendereintrag> postDVP(@PathVariable("kalenderId") final Long kalenderId, 
                                                   @RequestBody Kalendereintrag newKalendereintrag){
        LOGGER.info("CREATED NEW KALENDEREINTRAG!");
        
        Optional<Kalender> kalender = kalenderRepository.findById(kalenderId);
        
        if(!kalender.isPresent())
            throw new RuntimeException(format("kalender [%s] not found", kalenderId));
            
        newKalendereintrag.setKalender(kalender.get());
//        kalender.get().getKalendereintragListe().add(newKalendereintrag);

        LOGGER.info("Trying to save");
        LOGGER.info("Kalender" + kalender.get().toString() + " listen Laenge: " + kalender.get().getKalendereintragListe().size());
        
//        kalenderRepository.save(kalender.get());
        kalendereintragRepository.save(newKalendereintrag);
        LOGGER.info("Success!!!");

        return new ResponseEntity<>(newKalendereintrag, HttpStatus.CREATED);
    }


    @GetMapping("/k/{kalenderId}/ke/{kalendereintragId}")
    public ResponseEntity<?> getKalendereintrag(@PathVariable("kalenderId") final Long kalenderId,
                                                @PathVariable("kalendereintragId") final Long kalendereintragId) {
        final Iterable<Kalendereintrag> kalendereintragList = this.kalendereintragRepository.findAllByKalender_IdAndId(kalenderId, kalendereintragId);
        LOGGER.info("Repo count: " + kalendereintragRepository.count());

        Resources<Kalendereintrag> resources = new Resources<>(kalendereintragList);

        resources.add(linkTo(methodOn(KalendereintragController.class).getKalendereintrag(kalenderId, kalendereintragId)).withSelfRel());

        LOGGER.info("RETURN SPECIFIC KALENDER ENTRY: " + resources.getContent().size());
        return  ResponseEntity.ok(resources);
    }

//    private RuntimeException kalenderEintragNotFound(final Long id) {
//        throw new RuntimeException(format("getKalender [%s] not found", id));
//    }

    private RuntimeException kalenderNotFound(final Long id) {
        throw new RuntimeException(format("kalender [%s] not found", id));
    }
}
