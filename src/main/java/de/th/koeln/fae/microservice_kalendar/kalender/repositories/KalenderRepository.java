package de.th.koeln.fae.microservice_kalendar.kalender.repositories;

import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;


@RepositoryRestResource(path = "k")
public interface KalenderRepository extends CrudRepository<Kalender, UUID> {

    Iterable<Kalender> findAllByDvp_Id(UUID dvp_id);
}

