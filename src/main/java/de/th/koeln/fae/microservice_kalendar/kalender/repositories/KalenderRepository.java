package de.th.koeln.fae.microservice_kalendar.kalender.repositories;

import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(path = "k")
public interface KalenderRepository extends CrudRepository<Kalender, Long> {

    Iterable<Kalender> findAllById(Long id);
}
