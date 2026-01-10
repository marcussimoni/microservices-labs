package br.com.payment_service.services;

import br.com.payment_service.dtos.PaymentGatewayRequestDTO;
import br.com.payment_service.dtos.PaymentGatewayResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentGatewayService {

    private final String GATEWAY_URL;
    private final RestTemplate restTemplate;

    public PaymentGatewayService(
            @Value("${applications.payment-gateway.url}") String GATEWAY_URL, RestTemplate restTemplate) {
        this.GATEWAY_URL = GATEWAY_URL;
        this.restTemplate = restTemplate;
    }

    public PaymentGatewayResponseDTO sendPayment(PaymentGatewayRequestDTO request) {

        String url = this.GATEWAY_URL + "/transactions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentGatewayRequestDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PaymentGatewayResponseDTO> response = restTemplate.postForEntity(
                url,
                entity,
                PaymentGatewayResponseDTO.class
        );

        return response.getBody();
    }

}
