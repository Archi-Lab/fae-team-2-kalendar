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
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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


    @PostMapping("/k/{kalenderId}/ke")
    public ResponseEntity<?> postKalendereintrag(@PathVariable("kalenderId") final UUID kalenderId,
                                                               @RequestBody Kalendereintrag newKalendereintrag){

        Optional<Kalender> kalender = kalenderRepository.findById(kalenderId);

        if(kalender.isEmpty()) {
            LOGGER.error("Kalender " + kalenderId + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Kalender " + kalenderId + "doesn't exist");
        }
            
        newKalendereintrag.setKalender(kalender.get());
//        kalender.get().getKalendereintragListe().add(newKalendereintrag);
//
//        LOGGER.info("Trying to save");
//        LOGGER.info("Kalender" + kalender.get().toString() + " listen Laenge: " + kalender.get().getKalendereintragListe().size());
//
//        kalenderRepository.save(kalender.get());
        kalendereintragRepository.save(newKalendereintrag);
        LOGGER.info("Successfully created Kalendereintrag with id: " + newKalendereintrag.getId());

        return new ResponseEntity<>(newKalendereintrag, HttpStatus.CREATED);
    }


    @GetMapping("/k/{kalenderId}/ke/{kalendereintragId}")
    public ResponseEntity<?> getKalendereintrag(@PathVariable("kalenderId") final UUID kalenderId,
                                                @PathVariable("kalendereintragId") final UUID kalendereintragId) {
        final Optional<Kalendereintrag> kalendereintrag = kalendereintragRepository.findByKalender_IdAndId(kalenderId, kalendereintragId);

        if(kalendereintrag.isEmpty()) { return  ResponseEntity.notFound().build(); }

        Resource<Kalendereintrag> resource = new Resource<>(kalendereintrag.get());

        resource.add(linkTo(methodOn(KalendereintragController.class).getKalendereintrag(kalenderId, kalendereintragId)).withSelfRel());

        return  ResponseEntity.ok(resource);
    }


    @PutMapping("/k/{kalenderId}/ke/{kalendereintragId}")
    public ResponseEntity<?> putKalendereintrag(@PathVariable("kalenderId") final UUID kalenderId,
                                                @PathVariable("kalendereintragId") final UUID kalendereintragId,
                                                @RequestBody Kalendereintrag newKalendereintrag) {
        final Optional<Kalender> kalender = kalenderRepository.findById(kalenderId);

        if(kalender.isEmpty()) {
            LOGGER.error("Kalender " + kalenderId + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        newKalendereintrag.setKalender(kalender.get());

        Optional<Kalendereintrag> oldKalendereintrag = kalendereintragRepository.findByKalender_IdAndId(kalenderId, kalendereintragId);

        if(oldKalendereintrag.isPresent()){
            newKalendereintrag.setId(oldKalendereintrag.get().getId());
            kalendereintragRepository.save(newKalendereintrag);
            Resource<Kalendereintrag> resources = new Resource<>(oldKalendereintrag.get());

            resources.add(linkTo(methodOn(KalendereintragController.class).putKalendereintrag(kalenderId, kalendereintragId, newKalendereintrag)).withSelfRel());

            return ResponseEntity.ok(resources);
        }
        else{
            // This mode (creating at specified uuid) should not be supported
            LOGGER.error("Put method on non existent entries not supported");

            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body("\"Put\" method on non existent entries not supported. Use \"Post\" instead.");
        }
    }

    @Transactional
    @DeleteMapping("/k/{kalenderId}/ke/{kalendereintragId}")
    public ResponseEntity<?> deleteKalendereintrag(@PathVariable("kalenderId") final UUID kalenderId,
                                                @PathVariable("kalendereintragId") final UUID kalendereintragId) {
        Optional<Kalender> kalender = kalenderRepository.findById(kalenderId);

        if(kalender.isEmpty()) {
            LOGGER.error("Kalender " + kalenderId + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        kalendereintragRepository.deleteByKalender_IdAndId(kalenderId, kalendereintragId);

        return  ResponseEntity.noContent().build();
    }
}
