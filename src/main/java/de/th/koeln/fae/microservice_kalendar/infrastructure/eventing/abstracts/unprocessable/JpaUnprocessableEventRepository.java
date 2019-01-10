package de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.unprocessable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaUnprocessableEventRepository extends JpaRepository<UnprocessedEventEntity, String> {
    List<UnprocessedEventEntity> findAllByTopic(String topic);
}
