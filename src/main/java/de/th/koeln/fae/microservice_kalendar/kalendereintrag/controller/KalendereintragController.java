package de.th.koeln.fae.microservice_kalendar.kalendereintrag.controller;


import de.th.koeln.fae.microservice_kalendar.kalender.controller.KalenderController;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import de.th.koeln.fae.microservice_kalendar.kalender.repositories.KalenderRepository;
import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Kalendereintrag;
import de.th.koeln.fae.microservice_kalendar.kalendereintrag.repositories.KalendereintragRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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
    
//    @GetMapping("/k/{kalenderId}/ke")
//    public ResponseEntity<?> getKalendereintragList(@PathVariable("kalenderId") final UUID kalenderId) {
//        final Iterable<Kalendereintrag> kalendereintragList = this.kalendereintragRepository.findAllByKalender_Id(kalenderId);
//
//        Resources<Kalendereintrag> resources = new Resources<>(kalendereintragList);
//
//        resources.add(linkTo(methodOn(KalendereintragController.class).getKalendereintragList(kalenderId)).withSelfRel());
//
//        LOGGER.info("RETURN ALL KALENDER ENTRIES!");
//        return  ResponseEntity.ok(resources);
//    }

    @PostMapping("/k/{kalenderId}/ke")
    public ResponseEntity<?> postKalendereintrag(@PathVariable("kalenderId") final UUID kalenderId,
                                                               @RequestBody Kalendereintrag newKalendereintrag){

//        LOGGER.info("CREATING NEW KALENDEREINTRAG!");

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
        final Iterable<Kalendereintrag> kalendereintragList = this.kalendereintragRepository.findAllByKalender_IdAndId(kalenderId, kalendereintragId);
//        LOGGER.info("Repo count: " + kalendereintragRepository.count());

        Resources<Kalendereintrag> resources = new Resources<>(kalendereintragList);

        resources.add(linkTo(methodOn(KalendereintragController.class).getKalendereintrag(kalenderId, kalendereintragId)).withSelfRel());

//        LOGGER.info("RETURN SPECIFIC KALENDER ENTRY: " + resources.getContent().size());
        return  ResponseEntity.ok(resources);
    }


    @PutMapping("/k/{kalenderId}/ke/{kalendereintragId}")
    public ResponseEntity<?> putKalendereintrag(@PathVariable("kalenderId") final UUID kalenderId,
                                                @PathVariable("kalendereintragId") final UUID kalendereintragId,
                                                @RequestBody Kalendereintrag newKalendereintrag) {
        Optional<Kalender> kalender = kalenderRepository.findById(kalenderId);

        if(kalender.isEmpty()) {
            LOGGER.error("Kalender " + kalenderId + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        newKalendereintrag.setKalender(kalender.get());

        final Iterable<Kalendereintrag> kalendereintragList = this.kalendereintragRepository.findAllByKalender_IdAndId(kalenderId, kalendereintragId);
        LOGGER.info("Repo count: " + kalendereintragRepository.count());
        Optional<Kalendereintrag> oldKalendereintrag = this.kalendereintragRepository.findKalendereintragByKalender_IdAndId(kalenderId, kalendereintragId);

        if(oldKalendereintrag.isPresent()){
            newKalendereintrag.setId(oldKalendereintrag.get().getId());
            this.kalendereintragRepository.save(newKalendereintrag);
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

    @DeleteMapping("/k/{kalenderId}/ke/{kalendereintragId}")
    public ResponseEntity<?> deleteKalendereintrag(@PathVariable("kalenderId") final UUID kalenderId,
                                                @PathVariable("kalendereintragId") final UUID kalendereintragId) {
        Optional<Kalender> kalender = kalenderRepository.findById(kalenderId);

        if(kalender.isEmpty()) {
            LOGGER.error("Kalender " + kalenderId + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // TODO Löschen des alten Eintrags somehow
//        final Iterable<Kalendereintrag> kalendereintragList = this.kalendereintragRepository.findAllByKalender_IdAndId(kalenderId, kalendereintragId);
//        kalendereintragRepository.deleteAll(kalendereintragList);

        this.kalendereintragRepository.deleteByKalender_IdAndId(kalenderId, kalendereintragId);

        return  ResponseEntity.noContent().build();
    }

//    private RuntimeException kalenderEintragNotFound(final UUID id) {
//        throw new RuntimeException(format("getKalender [%s] not found", id));
//    }

    private RuntimeException kalenderNotFound(final UUID id) {
        throw new RuntimeException(format("kalender [%s] not found", id));
    }
}
