package de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.configuration;


public interface ConsumerTopicConfig {

    String getName();

    boolean isPayloadSensitive();

}
