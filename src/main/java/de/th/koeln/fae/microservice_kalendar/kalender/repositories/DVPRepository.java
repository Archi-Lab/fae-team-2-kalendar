package de.th.koeln.fae.microservice_kalendar.kalender.repositories;

import de.th.koeln.fae.microservice_kalendar.kalender.models.DVP;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface DVPRepository extends CrudRepository<DVP, UUID> {
}
