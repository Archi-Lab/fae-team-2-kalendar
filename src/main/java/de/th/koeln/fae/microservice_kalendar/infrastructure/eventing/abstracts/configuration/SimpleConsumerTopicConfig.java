package de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.configuration;

public class SimpleConsumerTopicConfig implements ConsumerTopicConfig {

    private String name;
    private boolean payloadSensitive = false;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isPayloadSensitive() {
        return payloadSensitive;
    }

    public void setPayloadSensitive(final boolean payloadSensitive){
        this.payloadSensitive = payloadSensitive;
    }
}
