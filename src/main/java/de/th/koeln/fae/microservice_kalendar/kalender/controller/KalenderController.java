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
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RepositoryRestController
public class KalenderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KalenderController.class);

    private final KalenderRepository kalenderRepository;
    private final DVPRepository dvpRepository;

    @Autowired
    public KalenderController(KalenderRepository kalenderRepository, DVPRepository dvpRepository) {
        this.kalenderRepository = kalenderRepository;
        this.dvpRepository = dvpRepository;
    }

    @GetMapping(path="/k")
    public ResponseEntity<?> getKalenders(
            @RequestParam(value="dvpid", required=true, defaultValue="") final String dvpidString){

        LOGGER.info("trying new get method");
        UUID dvpid = UUID.fromString(dvpidString);

        Optional<DVP> dvp = dvpRepository.findById(dvpid);
        if(dvp.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("DVP with ID \"" + dvpid + "\" does not exist");
        }

        Iterable<Kalender> kalenders = kalenderRepository.findAllByDvp_Id(dvpid);

        KalenderResourceAssembler assembler = new KalenderResourceAssembler();
        List<KalenderResource> kalenderResources = new ArrayList<>();

        for (Kalender kalender : kalenders) {
            kalenderResources.add(assembler.toResource(kalender));
        }

        Resources<KalenderResource> resources = new Resources<>(kalenderResources);

        return ResponseEntity.ok(resources);
    }

//    /**
//     * Simple Get Methode für einen spezifischen Kalender
//     *
//     * @param kalenderId ID des anzuzeigenden Kalenders
//     * @return Kalender-Objekt, Darstellung festgelegt durch {@link KalenderResourceAssembler}
//     */
//    @GetMapping("/k/{kalenderId}")
//    public ResponseEntity<?> getKalender(@PathVariable("kalenderId") final UUID kalenderId) {
//        Optional<Kalender> kalender = kalenderRepository.findById(kalenderId);
//        if(kalender.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        KalenderResourceAssembler assembler = new KalenderResourceAssembler();
//        KalenderResource resource = assembler.toResource(kalender.get());
//
//        return ResponseEntity.ok(resource);
//    }

    /**
     * Post Methode zum Anlegen eines neuen Kalenders
     *
     * @param newKalender Neu anzulegender Kalender, die Attribute
     *                    {@link de.th.koeln.fae.microservice_kalendar.kalender.models.Name}
     *                    {@link de.th.koeln.fae.microservice_kalendar.kalender.models.Zeitzone}
     *                    {@link de.th.koeln.fae.microservice_kalendar.kalender.models.DVP.DVP}
     *                    sind zu übergeben
     * @return Kalender-Objekt, Darstellung festgelegt durch {@link KalenderResourceAssembler}
     */
    @PostMapping("/k")
    public ResponseEntity<?> postKalender(@RequestBody Kalender newKalender) {
        Optional<DVP> referencedDVP = dvpRepository.findById(newKalender.getDvp().getId());
        if(referencedDVP.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("DVP with ID \"" + newKalender.getDvp().getId() + "\" does not exist");
        }
        newKalender.setDvp(referencedDVP.get());


        if (newKalender.getKalendereintragListe() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("\"Post\" method with kalendereintragListe not supported.");
        }
        newKalender.setKalendereintragListe(new ArrayList<>());


        kalenderRepository.save(newKalender);

        KalenderResourceAssembler assembler = new KalenderResourceAssembler();
        KalenderResource resource = assembler.toResource(newKalender);

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Put Methode zum Updaten eines spezifischen Kalenders
     *
     * @param kalenderId ID des zu ändernden Kalenders
     * @param newKalender Zu ändernder Kalender, die Attribute
     *                    {@link de.th.koeln.fae.microservice_kalendar.kalender.models.Name},
     *                    {@link de.th.koeln.fae.microservice_kalendar.kalender.models.Zeitzone} und
     *                    {@link de.th.koeln.fae.microservice_kalendar.kalender.models.DVP.DVP}
     *                    sind zu übergeben
     * @return Kalender-Objekt, Darstellung festgelegt durch {@link KalenderResourceAssembler}
     */
    @PutMapping("/k/{kalenderId}")
    public ResponseEntity<?> putKalender(@PathVariable("kalenderId") final UUID kalenderId,
                                         @RequestBody Kalender newKalender) {
        final Optional<Kalender> oldKalender = kalenderRepository.findById(kalenderId);
        if (oldKalender.isEmpty()) {
            // This mode (creating at specified uuid) should not be supported
            LOGGER.error("Put method on non existent entries not supported");

            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body("\"Put\" method on non existent entries not supported. Use \"Post\" instead.");
        }

        final Optional<DVP> referencedDVP = dvpRepository.findById(newKalender.getDvp().getId());
        if (referencedDVP.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("DVP with ID \"" + newKalender.getDvp().getId() + "\" does not exist");
        }
        oldKalender.get().setDvp(referencedDVP.get());

        if (newKalender.getKalendereintragListe() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("\"Put\" method with kalendereintragListe not supported.");
        }

        oldKalender.get().setName(newKalender.getName());
        oldKalender.get().setZeitzone(newKalender.getZeitzone());

        kalenderRepository.save(oldKalender.get());

        KalenderResourceAssembler assembler = new KalenderResourceAssembler();
        KalenderResource resource = assembler.toResource(oldKalender.get());

        return ResponseEntity.ok(resource);
    }

    /**
     * Delete Methode zum Löschen eines spezifischen Kalenders
     *
     * @param kalenderId ID des zu löschenden Kalenders
     * @return HTTP Status 204 (No Content)
     */
    @DeleteMapping("/k/{kalenderId}")
    public ResponseEntity<?> deleteKalender(@PathVariable("kalenderId") final UUID kalenderId) {
        kalenderRepository.deleteById(kalenderId);
        return  ResponseEntity.noContent().build();
    }
}
