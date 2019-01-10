package de.th.koeln.fae.microservice_kalendar.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaGateway.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String topic;

    @Autowired
    public KafkaGateway(final KafkaTemplate<String, String> kafkaTemplate, final ObjectMapper objectMapper,
                        @Value("${eventing.topic_name}") final String topic){
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.topic = topic;
    }

    public ListenableFuture<SendResult<String, String>> publishTrackingEvent(KalenderEvent kalenderEvent) {
        LOGGER.info("publishing event {} to topic {}", kalenderEvent.getId(), topic);
        return kafkaTemplate.send(topic, kalenderEvent.getKey(), toAsiEventMessage(kalenderEvent));
    }

    private String toAsiEventMessage(KalenderEvent kalenderEvent){
        try{
            final Map<String, Object> message = new HashMap<>();
            message.put("id", kalenderEvent.getId());
            message.put("key", kalenderEvent.getKey());
            message.put("time", kalenderEvent.getTime());
            message.put("type", kalenderEvent.getType());
            message.put("version", kalenderEvent.getVersion());
            message.put("payload", objectMapper.readValue(kalenderEvent.getPayload(objectMapper), kalenderEvent.getEntityType()));
            return objectMapper.writeValueAsString(message);
        } catch (final JsonProcessingException e) {
            LOGGER.error("Could not serialize event with id {}", kalenderEvent.getId(), e);
            return "";
        } catch (IOException e){
            LOGGER.error("Could not read payload for event with id {}", kalenderEvent.getId(), e);
            return "";
        }
    }
}
