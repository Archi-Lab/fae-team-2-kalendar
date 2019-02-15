package de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.exception.MessageProcessingException;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.exception.PermanentMessageProcessingException;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.exception.TemporaryMessageProcessingException;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.exception.UnexpectedMessageProcessingException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

@Component
public class EventParser {

    private final ObjectMapper objectMapper;
    private final Validator validator;

    @Inject
    public EventParser(final ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper");
        //objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    <P extends EventPayload, M extends DomainEvent<P>> M parseMessage(final String message, final Class<M> messageType)
            throws MessageProcessingException {
        final M kafkaMessage = deserialize(message, messageType);
        validate(kafkaMessage);
        return kafkaMessage;
    }

    private <P extends EventPayload, M extends DomainEvent<P>> M deserialize(final String message, final Class<M> messageType)
            throws MessageProcessingException {
        try {
            return objectMapper.readValue(message, messageType);
        } catch (final JsonParseException e) {
            throw new PermanentMessageProcessingException("Failed to parse message as JSON.", e);
        } catch (final JsonMappingException e) {
            throw new UnexpectedMessageProcessingException(
                    format("Failed to parse message as %s.", messageType.getSimpleName()), e);
        } catch (final IOException e) {
            throw new TemporaryMessageProcessingException("A low-level I/O problem occurred while parsing.", e);
        }
    }

    public void validate(final Object message) throws MessageProcessingException {
        final Set<ConstraintViolation<Object>> violations = validator.validate(message);
        if (!violations.isEmpty()) {
            final String violationMessages = violations.stream()
                    .map(violation -> format("%s %s", violation.getPropertyPath(), violation.getMessage()))
                    .collect(joining(format("%n\t")));
            throw new UnexpectedMessageProcessingException(format("Received invalid %s: %n\t%s",
                    message.getClass().getSimpleName(), violationMessages));
        }
    }
}
