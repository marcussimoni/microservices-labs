package br.com.shipping_service.services;

import br.com.shipping_service.dtos.OrderDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BookstoreService {

    private final RestTemplate restTemplate;
    private final String bookstoreHost;

    public BookstoreService(
            @Value("${applications.bookstore.host}") String bookstoreHost,
            RestTemplate restTemplate
    ) {
        this.bookstoreHost = bookstoreHost;
        this.restTemplate = restTemplate;
    }

    public OrderDTO getOrderById(Long id) {
        String url = UriComponentsBuilder
                .fromHttpUrl(bookstoreHost)
                .path("/orders/id/{id}")
                .buildAndExpand(id)
                .toUriString();

        return restTemplate.getForObject(url, OrderDTO.class);
    }

}
