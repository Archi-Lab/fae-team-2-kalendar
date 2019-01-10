package de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.exception;


import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.EventProcessingState;

public class PermanentMessageProcessingException extends MessageProcessingException{

    private static final long serialVersionUID = 1L;

    public PermanentMessageProcessingException(final String message, final Exception e) {
        super(EventProcessingState.PERMANENT_ERROR, message, e);
    }
}
