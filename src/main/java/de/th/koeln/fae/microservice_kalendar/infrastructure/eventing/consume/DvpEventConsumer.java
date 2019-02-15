package de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.consume;

import com.google.common.collect.ImmutableSet;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.AbstractKafkaConsumer;
import de.th.koeln.fae.microservice_kalendar.infrastructure.eventing.abstracts.unprocessable.UnprocessableEventService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;

import javax.inject.Inject;

@Component
public class DvpEventConsumer extends AbstractKafkaConsumer {

    @Inject
    protected DvpEventConsumer(DvpEventProcessor messageProcessor, UnprocessableEventService unprocessableEventService) {
        super(messageProcessor, unprocessableEventService,
                ImmutableSet.of(UncategorizedDataAccessException.class, TransientDataAccessException.class,
                        CannotCreateTransactionException.class));
    }

    @KafkaListener(topics = "${eventing.topic_dvp_consumer_name}")
    public void listen(final ConsumerRecord<String, String> consumerRecord, final Acknowledgment ack) {
        super.handleConsumerRecord(consumerRecord, ack);
    }
}
