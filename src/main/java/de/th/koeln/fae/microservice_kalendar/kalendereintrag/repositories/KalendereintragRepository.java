package de.th.koeln.fae.microservice_kalendar.kalendereintrag.repositories;


import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Kalendereintrag;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface KalendereintragRepository extends CrudRepository<Kalendereintrag, UUID> {

    Optional<Kalendereintrag> findByKalender_IdAndId(UUID kalender_Id, UUID id);

    void deleteByKalender_IdAndId(UUID kalender_id, UUID id);
}
