package de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts;

public enum EventProcessingState {

    SUCCESS(true), UNEXPECTED_ERROR(false), TEMPORARY_ERROR(false), PERMANENT_ERROR(true);

    private final boolean finalState;

    EventProcessingState(final boolean finalState) {
        this.finalState = finalState;
    }

    public boolean isFinalState() {
        return finalState;
    }
}
