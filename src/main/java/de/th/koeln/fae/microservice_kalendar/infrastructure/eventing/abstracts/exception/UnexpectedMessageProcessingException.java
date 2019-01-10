package de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.exception;


import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.EventProcessingState;

public class UnexpectedMessageProcessingException extends MessageProcessingException{

    private static final long serialVersionUID = 1L;

    public UnexpectedMessageProcessingException(final String message, final Exception e) {
        super(EventProcessingState.UNEXPECTED_ERROR, message, e);
    }

    public UnexpectedMessageProcessingException(final String message) {
        super(EventProcessingState.UNEXPECTED_ERROR, message);
    }

}
