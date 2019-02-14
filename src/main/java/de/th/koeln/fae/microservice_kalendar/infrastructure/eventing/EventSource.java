package de.th.koeln.fae.microservice_dementiell_veraenderter.infrastructure.eventing;

public interface EventSource {
    
    String getId();
    Long getVersion();
    String getAggregateName();

}
