package de.th.koeln.fae.microservice_kalendar.infrastructure;

import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.AbstractDomainEventProcessor;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.EventParser;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.EventProcessingState;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.configuration.ConsumerTopicConfig;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.processed.ProcessedEventService;
import de.th.koeln.fae.microservice_kalendar.kalender.models.DVP;
import de.th.koeln.fae.microservice_kalendar.kalender.repositories.DVPRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class DvpEventProcessor extends AbstractDomainEventProcessor<DvpPayload, DvpEvent>{

    private static final Logger LOG = LoggerFactory.getLogger(AbstractDomainEventProcessor.class);

    private final DVPRepository repository;

    @Inject
    public DvpEventProcessor(final ConsumerTopicConfig dvpTopicConfig, final EventParser eventParser, final ProcessedEventService processedEventService, final DVPRepository repository) {
        super(DvpEvent.class, dvpTopicConfig, eventParser, processedEventService);
        this.repository = repository;
    }

    @Override
    protected EventProcessingState processEvent(final DvpEvent dvpEvent) {
        switch (dvpEvent.getType()) {
            case "dvp-created":
            case "dvp-updated":
                repository.save(toDvp(dvpEvent));
                LOG.info("dvp saved");
                break;
            default:
                LOG.warn("Unexpected type: '{}' of message with key '{}'", dvpEvent.getType(),
                        dvpEvent.getKey());
                return EventProcessingState.UNEXPECTED_ERROR;
        }
        return EventProcessingState.SUCCESS;
    }

    private DVP toDvp(final DvpEvent dvpEvent) {
        final DVP dvp = new DVP();
        dvp.setId(Long.valueOf(dvpEvent.getPayload().getId()));

        return dvp;
    }
}
