package de.th.koeln.fae.microservice_kalendar.kalendereintrag.repositories;


import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Kalendereintrag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

//@RepositoryRestResource(path = "ke")
public interface KalendereintragRepository extends CrudRepository<Kalendereintrag, UUID> {

//    Iterable<Kalendereintrag> findAllById(UUID id);
    Iterable<Kalendereintrag> findAllByKalender_Id(UUID kalender_Id);
//    Iterable<Kalendereintrag> findAllByKalender_IdAndId(UUID kalender_Id, UUID id);
//    Optional<Kalendereintrag> findKalendereintragByKalender_IdAndId(UUID kalender_Id, UUID id);
    Optional<Kalendereintrag> findByKalender_IdAndId(UUID kalender_Id, UUID id);


    void deleteByKalender_IdAndId(UUID kalender_id, UUID id);
}
