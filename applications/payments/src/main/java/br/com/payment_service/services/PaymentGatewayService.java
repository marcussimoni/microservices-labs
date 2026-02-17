package br.com.payment_service.services;

import br.com.payment_service.dtos.PaymentGatewayRequestDTO;
import br.com.payment_service.dtos.PaymentGatewayResponse;
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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

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
            retryFor = HttpServerErrorException.class,
            maxAttempts = 5,
            backoff = @Backoff(
                   delay = 2000,
                    multiplier = 2.0
            )
    )
    public PaymentGatewayResponse sendPayment(PaymentGatewayRequestDTO request) {

        log.info("Init payment execution");

        String url = this.GATEWAY_URL + "/v1/payments/transaction";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentGatewayRequestDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PaymentGatewayResponse> response;

        try {
            response = restTemplate.postForEntity(
                    url,
                    entity,
                    PaymentGatewayResponse.class
            );
        } catch (HttpServerErrorException e) {
            var error = e.getResponseBodyAs(PaymentGatewayResponse.class);
            log.error("Caught an HttpServerErrorException: {} while sending payment. Retrying request", error.error().code());
            throw e;
        }

        if (response.getStatusCode().is5xxServerError()) {
            throw new HttpServerErrorException(response.getStatusCode());
        }

        log.info("Payment successful");

        return response.getBody();
    }

    @Recover
    private PaymentGatewayResponse paymentGatewayFallback(RuntimeException exception) {
        log.error("Fail to request to payment gateway", exception);
        throw exception;
    }

    @CircuitBreaker(
            name = "retrieve-payments",
            fallbackMethod = "listPaymentsGatewayFallback"
    )
    public List<PaymentGatewayResponse> listPaymentsGateway() {

        log.info("Init payment execution");
        String url = this.GATEWAY_URL + "/payments";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var type = new ParameterizedTypeReference<List<PaymentGatewayResponse>>() {};
        ResponseEntity<List<PaymentGatewayResponse>> payments = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), type);

        return payments.getBody();


    }

    private List<PaymentGatewayResponse> listPaymentsGatewayFallback(Exception exception) {
        log.info("Init fallback payment execution");
        return List.of();
    }

}
