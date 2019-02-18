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
    public KalendereintragController(KalendereintragRepository kalendereintragRepository, KalenderRepository kalenderRepository) {
        this.kalendereintragRepository = kalendereintragRepository;
        this.kalenderRepository = kalenderRepository;
    }

    /**
     * Simple Get Methode für einen spezifischen Kalendereintrag
     *
     * @param kalenderId ID des zugehörigen Kalenders
     * @param kalendereintragId ID des anzuzeigenden Kalendereintrags
     * @return Kalendereintrag-Objekt
     */
    @GetMapping("/k/{kalenderId}/ke/{kalendereintragId}")
    public ResponseEntity<?> getKalendereintrag(@PathVariable("kalenderId") final UUID kalenderId,
                                                @PathVariable("kalendereintragId") final UUID kalendereintragId) {
        final Optional<Kalendereintrag> kalendereintrag = kalendereintragRepository.findByKalender_IdAndId(kalenderId, kalendereintragId);

        if(kalendereintrag.isEmpty()) { return  ResponseEntity.notFound().build(); }

        Resource<Kalendereintrag> resource = new Resource<>(kalendereintrag.get());

        resource.add(linkTo(methodOn(KalendereintragController.class).getKalendereintrag(kalenderId, kalendereintragId)).withSelfRel());

        return  ResponseEntity.ok(resource);
    }

    /**
     * Post Methode zum Anlegen eines neuen Kalenders
     *
     * @param kalenderId ID des zugehörigen Kalenders
     * @param newKalendereintrag Neu anzulegender Kalendereintrag
     * @return Kalendereintrag-Objekt
     */
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

        kalendereintragRepository.save(newKalendereintrag);
        LOGGER.info("Successfully created Kalendereintrag with id: " + newKalendereintrag.getId());

        Resource<Kalendereintrag> resource = new Resource<>(newKalendereintrag);
        resource.add(linkTo(methodOn(KalendereintragController.class).getKalendereintrag(kalenderId, newKalendereintrag.getId())).withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Put Methode zum Updaten eines spezifischen Kalenders
     *
     * @param kalenderId ID des zugehörigen Kalenders
     * @param kalendereintragId ID des zu ändernden Kalendereintrags
     * @param newKalendereintrag Zu ändernder Kalendereintrag
     * @return Kalendereintrag-Objekt
     */
    @PutMapping("/k/{kalenderId}/ke/{kalendereintragId}")
    public ResponseEntity<?> putKalendereintrag(@PathVariable("kalenderId") final UUID kalenderId,
                                                @PathVariable("kalendereintragId") final UUID kalendereintragId,
                                                @RequestBody Kalendereintrag newKalendereintrag) {
        final Optional<Kalender> kalender = kalenderRepository.findById(kalenderId);
        if (kalender.isEmpty()) {
            LOGGER.error("Kalender " + kalenderId + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        newKalendereintrag.setKalender(kalender.get());

        Optional<Kalendereintrag> oldKalendereintrag = kalendereintragRepository.findByKalender_IdAndId(kalenderId, kalendereintragId);
        if(oldKalendereintrag.isEmpty()){
            // This mode (creating at specified uuid) should not be supported
            LOGGER.error("Put method on non existent entries not supported");

            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body("\"Put\" method on non existent entries not supported. Use \"Post\" instead.");
        }
        newKalendereintrag.setId(oldKalendereintrag.get().getId());
        kalendereintragRepository.save(newKalendereintrag);
        Resource<Kalendereintrag> resources = new Resource<>(newKalendereintrag);

        resources.add(linkTo(methodOn(KalendereintragController.class).putKalendereintrag(kalenderId, kalendereintragId, newKalendereintrag)).withSelfRel());

        return ResponseEntity.ok(resources);
    }

    /**
     * Delete Methode zum Löschen eines spezifischen Kalendereintrags
     *
     * @param kalenderId ID des zugehörigen Kalenders
     * @param kalendereintragId ID des zu löschenden Kalendereintrags
     * @return HTTP Status 204 (No Content)
     */
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
