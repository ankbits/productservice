package com.myretail.api.product.infrastructure.service.productdetails;

import com.myretail.api.product.domain.service.productdetails.ProductDetails;
import com.myretail.api.product.domain.service.productdetails.ProductDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Value("${product.details.service.base-url}")
    private String productDetailsServiceBaseURL;


    @Override
    public Mono<ProductDetails> getProductDetails(String productId) {

        WebClient client = WebClient.builder()
                .baseUrl(productDetailsServiceBaseURL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return client.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path("/{id}")
                        .queryParam("id_type", "TCIN  ")
                        .queryParam("fields", "description")
                        .queryParam("key", "123")
                        .build(productId))
                .retrieve()
                .bodyToMono(ProductDetails.class);

    }
}
