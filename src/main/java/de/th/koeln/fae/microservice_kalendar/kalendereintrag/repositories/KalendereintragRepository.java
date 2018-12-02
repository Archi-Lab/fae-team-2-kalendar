package de.th.koeln.fae.microservice_kalendar.kalendereintrag.repositories;


import de.th.koeln.fae.microservice_kalendar.kalendereintrag.models.Kalendereintrag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "ke")
public interface KalendereintragRepository extends CrudRepository<Kalendereintrag, Long> {

    Iterable<Kalendereintrag> findAllById(Long id);
}
