package de.th.koeln.fae.microservice_kalendar.infrastructure.abstracts.configuration;


public interface ConsumerTopicConfig {

    String getName();

    boolean isPayloadSensitive();

}
