package de.th.koeln.fae.microservice_kalendar.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.fae.microservice_kalendar.kalender.models.Kalender;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class KalenderCreatedEvent implements KalenderEvent {

    public KalenderCreatedEvent(Kalender kalender)
    {
        this.id = (long)Math.random();
        this.kalender = kalender;
        this.instant = Instant.now();
    }

    final long id;
    final Kalender kalender;
    final Instant instant;

    public long getId() {
        return id;
    }

    public String getKey() {
        return Long.toString(kalender.getId());
    }

    @Override
    public ZonedDateTime getTime() {
        return instant.atZone(ZoneId.systemDefault());
    }

    //The tracker Entity doesn't implement any versioning patterns (yet), therefore event versions are always 0
    public Long getVersion() {
        return 0L;
    }

    public byte[] getPayload(ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(kalender);
    }

    public Class<?> getEntityType() {
        return kalender.getClass();
    }

    public String getType()
    {
        return "kalender-created";
    }
}
