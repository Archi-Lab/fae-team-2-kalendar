package de.th.koeln.fae.microservice_kalendar.infrastructure.abstracts.exception;

public class TemporaryKafkaProcessingError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TemporaryKafkaProcessingError(final String message) {
        super(message);
    }
}
