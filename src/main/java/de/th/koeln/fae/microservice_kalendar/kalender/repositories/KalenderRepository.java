package de.th.koeln.fae.microservice_kalendar.kalender.repositories;

import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.imageio.stream.IIOByteBuffer;
import java.util.Optional;
import java.util.UUID;


@RepositoryRestResource(path = "k")
public interface KalenderRepository extends CrudRepository<Kalender, UUID> {

    Optional<Kalender> findById(UUID id);
    void deleteById(UUID id);
}
