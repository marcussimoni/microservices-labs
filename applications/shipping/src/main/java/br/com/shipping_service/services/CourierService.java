package br.com.shipping_service.services;

import br.com.shipping_service.dtos.CourierQuoteResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
public class CourierService {

    private final RestTemplate restTemplate;
    private final String courierCompaniesHost;
    private final Logger logger = LoggerFactory.getLogger(CourierService.class);

    public static final String COURIER_QUOTES_CB = "courierQuotes";

    public CourierService(
            RestTemplate restTemplate,
            @Value("${applications.courier-companies.host}") String courierCompaniesHost) {
        this.restTemplate = restTemplate;
        this.courierCompaniesHost = courierCompaniesHost;
    }

    @Retry(
        name = COURIER_QUOTES_CB, fallbackMethod = "courierQuoteFallback"
    )
    public CourierQuoteResponse courierQuote() {

        var type = new ParameterizedTypeReference<List<CourierQuoteResponse>>() {};

        var responseEntity = this.restTemplate
                .exchange(courierCompaniesHost, HttpMethod.GET, null, type);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            List<CourierQuoteResponse> courierQuoteResponses = responseEntity.getBody();
            return courierQuoteResponses.stream()
                    .min(Comparator.comparing(CourierQuoteResponse::price))
                    .orElseThrow();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }

    public CourierQuoteResponse courierQuoteFallback(Throwable e) {
        logger.error("Resilience fallback triggered for Reason: {}",  e.getMessage());
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }

}
