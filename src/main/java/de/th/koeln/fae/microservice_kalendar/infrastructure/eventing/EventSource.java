package de.th.koeln.fae.microservice_kalendar.infrastructure.eventing;

import java.util.UUID;

public interface EventSource {
    
    UUID getId();
    Long getVersion();
    String getAggregateName();

}
