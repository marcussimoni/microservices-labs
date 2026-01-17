package br.com.payment_service;

import br.com.payment_service.configs.JacksonConfig;
import br.com.payment_service.dtos.KafkaOutboxMessage;
import br.com.payment_service.dtos.PaymentRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.lang.model.type.ReferenceType;

public class JsonDeserializationTest {

    private final String KAFKA_EVENT = """
            {
              "schema": {
                "type": "struct",
                "fields": [
                  {
                    "type": "int64",
                    "optional": false,
                    "default": 0,
                    "field": "id"
                  },
                  {
                    "type": "int32",
                    "optional": false,
                    "field": "purchase_id"
                  },
                  {
                    "type": "bytes",
                    "optional": false,
                    "name": "org.apache.kafka.connect.data.Decimal",
                    "version": 1,
                    "parameters": {
                      "scale": "2",
                      "connect.decimal.precision": "10"
                    },
                    "field": "amount"
                  },
                  {
                    "type": "string",
                    "optional": true,
                    "field": "book"
                  },
                  {
                    "type": "string",
                    "optional": true,
                    "field": "public_identifier"
                  }
                ],
                "optional": false,
                "name": "postgres.bookstore.purchase_outbox.Value"
              },
              "payload": {
                "id": 9,
                "purchase_id": 9,
                "amount": "10.0",
                "book": "The Hobbit",
                "public_identifier": "d858de5b-ed02-4c89-ac6a-d899a698d4de"
              }
          }
          """;

    private final JacksonConfig config = new JacksonConfig();
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        objectMapper = config.objectMapperConfig();
    }

    @Test
    public void mustDeserializeKafkaEvent() throws JsonProcessingException {

        TypeReference<KafkaOutboxMessage<PaymentRequestDTO>> typeReference = new TypeReference<>() {
        };
        KafkaOutboxMessage kafkaOutboxMessage = objectMapper.readValue(KAFKA_EVENT, typeReference);

        Assertions.assertNotNull(kafkaOutboxMessage.getPayload());

    }

}
