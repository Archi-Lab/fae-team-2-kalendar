package de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.processed;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProcessedEventRepository extends JpaRepository<ProcessedEventEntity, String> {

    ProcessedEventEntity findByTopicAndKey(String topic, String key);
}