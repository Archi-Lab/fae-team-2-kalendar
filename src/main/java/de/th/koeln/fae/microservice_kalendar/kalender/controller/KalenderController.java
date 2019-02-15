package de.th.koeln.fae.microservice_kalendar.kalender.controller;


import de.th.koeln.fae.microservice_kalendar.kalender.models.DVP.DVP;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import de.th.koeln.fae.microservice_kalendar.kalender.repositories.DVPRepository;
import de.th.koeln.fae.microservice_kalendar.kalender.repositories.KalenderRepository;
import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Kalendereintrag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
public class KalenderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KalenderController.class);

    @Autowired
    private KalenderRepository kalenderRepository;

    @Autowired
    private DVPRepository dvpRepository;

    @GetMapping("/k/{kalenderId}")
    public ResponseEntity<?> getKalender(@PathVariable("kalenderId") final UUID kalenderId) {
        Optional<Kalender> kalender = kalenderRepository.findById(kalenderId);

        if(kalender.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        KalenderResourceAssembler assembler = new KalenderResourceAssembler();
        KalenderResource resource = assembler.toResource(kalender.get());

        LOGGER.info("RETURN SPECIFIC KALENDER!");
        return ResponseEntity.ok(resource);
    }

    @PostMapping("/k")
    public ResponseEntity<?> postKalender(@RequestBody Kalender newKalender) {
        Optional<DVP> referencedDVP = dvpRepository.findById(newKalender.getDvp().getId());
        if(referencedDVP.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("DVP with ID \"" + newKalender.getDvp().getId() + "\" does not exist");
        } else {
            newKalender.setDvp(referencedDVP.get());
        }

        if (newKalender.getKalendereintragListe() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("\"Post\" method with kalendereintragListe not supported.");
        } else {
            newKalender.setKalendereintragListe(new ArrayList<>());
        }

        kalenderRepository.save(newKalender);

        KalenderResourceAssembler assembler = new KalenderResourceAssembler();
        KalenderResource resource = assembler.toResource(newKalender);

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @PutMapping("/k/{kalenderId}")
    public ResponseEntity<?> putKalender(@PathVariable("kalenderId") final UUID kalenderId,
                                         @RequestBody Kalender newKalender) {
        Optional<Kalender> oldKalender = kalenderRepository.findById(kalenderId);

        if(oldKalender.isPresent()) {
            Optional<DVP> referencedDVP = dvpRepository.findById(newKalender.getDvp().getId());
            if(referencedDVP.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("DVP with ID \"" + newKalender.getDvp().getId() + "\" does not exist");
            } else {
                newKalender.setDvp(referencedDVP.get());
            }

            if (newKalender.getKalendereintragListe() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("\"Put\" method with kalendereintragListe not supported.");
            } else {
                newKalender.setKalendereintragListe(oldKalender.get().getKalendereintragListe());
            }

            newKalender.setId(oldKalender.get().getId());
            kalenderRepository.save(newKalender);

            KalenderResourceAssembler assembler = new KalenderResourceAssembler();
            KalenderResource resource = assembler.toResource(newKalender);

            return ResponseEntity.ok(resource);
        }
        else {
            // This mode (creating at specified uuid) should not be supported
            LOGGER.error("Put method on non existent entries not supported");

            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body("\"Put\" method on non existent entries not supported. Use \"Post\" instead.");
        }
    }

    @DeleteMapping("/k/{kalenderId}")
    public ResponseEntity<?> deleteKalender(@PathVariable("kalenderId") final UUID kalenderId) {
//        final Iterable<Kalender> kalenderList = kalenderRepository.findAllById(kalenderId);

        kalenderRepository.deleteById(kalenderId);
        return  ResponseEntity.noContent().build();
    }
}
