package br.com.bookstore.commons.utils;

import br.com.bookstore.commons.configs.ObjectMapperConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageHeaders;

import java.util.UUID;

public class KafkaCommonsUtils {

    private static final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(KafkaCommonsUtils.class);

    static  {
        objectMapper = ObjectMapperConfig.objectMapperConfig();
    }

    public static UUID getIdempotencyKey(MessageHeaders headers) {
        return UUID.fromString(new String(headers.get("idempotency_key", byte[].class)));
    }

    public static <T> T getPayload(String message, Class<T> payloadClass) {
        try {

            JsonNode payload = objectMapper.readTree(message).get("payload");

            return objectMapper.treeToValue(payload, payloadClass);

        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static <T> T getObject(String message, Class<T> payloadClass) {
        try {

            return objectMapper.readValue(message, payloadClass);

        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
