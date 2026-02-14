package br.com.payment_service.services;

import br.com.payment_service.dtos.PaymentGatewayRequestDTO;
import br.com.payment_service.dtos.PaymentGatewayResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Service
public class PaymentGatewayService {

    private final String GATEWAY_URL;
    private final RestTemplate restTemplate;
    private final Logger log;

    public PaymentGatewayService(
            @Value("${applications.payment-gateway.url}") String GATEWAY_URL, RestTemplate restTemplate) {
        this.GATEWAY_URL = GATEWAY_URL;
        this.restTemplate = restTemplate;
        this.log = LoggerFactory.getLogger(PaymentGatewayService.class);
    }

    @Retryable(
            retryFor = RuntimeException.class,
            maxAttempts = 5,
            backoff = @Backoff(
                   delay = 2000,
                    multiplier = 2.0
            )
    )
    public PaymentGatewayResponseDTO sendPayment(PaymentGatewayRequestDTO request) {

        log.info("Init payment execution");

        String url = this.GATEWAY_URL + "/transactions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentGatewayRequestDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PaymentGatewayResponseDTO> response = restTemplate.postForEntity(
                url,
                entity,
                PaymentGatewayResponseDTO.class
        );

        log.info("Init payment successful");

        return response.getBody();
    }

    @Recover
    private PaymentGatewayResponseDTO paymentGatewayFallback(RuntimeException exception) {
        log.error("Fail to request to payment gateway", exception);
        throw exception;
    }

    @CircuitBreaker(
            name = "retrieve-payments",
            fallbackMethod = "listPaymentsGatewayFallback"
    )
    public List<PaymentGatewayResponseDTO> listPaymentsGateway() {

        log.info("Init payment execution");
        String url = this.GATEWAY_URL + "/payments";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var type = new ParameterizedTypeReference<List<PaymentGatewayResponseDTO>>() {};
        ResponseEntity<List<PaymentGatewayResponseDTO>> payments = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), type);

        return payments.getBody();


    }

    private List<PaymentGatewayResponseDTO> listPaymentsGatewayFallback(Exception exception) {
        log.info("Init fallback payment execution");
        return List.of();
    }

}
