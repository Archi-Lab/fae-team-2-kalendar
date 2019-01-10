package de.th.koeln.fae.microservice_kalendar.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.DomainEvent;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DvpEvent extends DomainEvent<DvpPayload> {


    private final String aggregateName;

    public DvpEvent(@JsonProperty("id") final String id, @JsonProperty("key") final String key,
                        @JsonProperty("time") final String time, @JsonProperty("type") final String type,
                        @JsonProperty("payload") final DvpPayload payload, @JsonProperty("version") final Long version,
                        @JsonProperty("aggregateName") final String aggregateName) {
        super(id, key, time, type, payload, version);
        this.aggregateName = aggregateName;
    }

    public String getAggregateName() {
        return aggregateName;
    }
}
