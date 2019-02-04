package de.th.koeln.fae.microservice_kalendar.kalender.controller;


import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import de.th.koeln.fae.microservice_kalendar.kalender.repositories.KalenderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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
    
    
    @PostMapping("/k")
    public ResponseEntity<?> postKalender(@RequestBody Kalender newKalender) {
        kalenderRepository.save(newKalender);

        KalenderResourceAssembler assembler = new KalenderResourceAssembler();
        KalenderResource resource = assembler.toResource(newKalender);

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping("/k/{kalenderId}")
    public ResponseEntity<?> getKalender(@PathVariable("kalenderId") final UUID kalenderId) {
        Optional<Kalender> kalender = kalenderRepository.findById(kalenderId);

        if(kalender.isEmpty()) {
            return  ResponseEntity.notFound().build();
        }

        KalenderResourceAssembler assembler = new KalenderResourceAssembler();
        KalenderResource resource = assembler.toResource(kalender.get());
//        Resource<Kalender> resource = new Resource<>(kalender.get());

//        resource.add(linkTo(methodOn(KalenderController.class).getKalender(kalenderId)).withSelfRel());
//        for (final Kalendereintrag kalendereintrag : kalender.get().getKalendereintragListe()) {
//            resource.add(linkTo(methodOn(KalendereintragController.class).getKalendereintrag(kalender.get().getId(),
//                                kalendereintrag.getId()))
//                        .withRel(kalendereintrag.getId().toString()));
//        }

        LOGGER.info("RETURN SPECIFIC KALENDER!");
        return  ResponseEntity.ok(resource);
    }

    @PutMapping("/k/{kalenderId}")
    public ResponseEntity<?> putKalender(@PathVariable("kalenderId") final UUID kalenderId,

                                         @RequestBody Kalender newKalender) {
        Optional<Kalender> oldKalender = kalenderRepository.findById(kalenderId);

        if(oldKalender.isPresent()) {
            newKalender.setId(oldKalender.get().getId());
            kalenderRepository.save(newKalender);

//            Resource<?> resource = new Resource<>(newKalender);
//            resource.add(linkTo(methodOn(KalenderController.class).putKalender(kalenderId, newKalender)).withSelfRel());

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
