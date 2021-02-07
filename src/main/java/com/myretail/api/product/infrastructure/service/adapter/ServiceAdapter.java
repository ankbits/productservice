package com.myretail.api.product.infrastructure.service.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

@Component
@RequiredArgsConstructor
public class ServiceAdapter {

    private static final Logger logger = Loggers.getLogger(ServiceAdapter.class);

    private final WebClient.Builder webClientBuilder;


    public <T> Mono<T> makeGETCall(String apiBaseURL, String queryString, Class<T> apiResponseClass) {
        return getRequestBodySpec(HttpMethod.GET, apiBaseURL, queryString)
                .retrieve()
                .bodyToMono(apiResponseClass)
                .doOnNext(apiResponse -> logger.info("MSG=execute GET request success, action=GetApi, URL={}, queryString={}, statusCode={}", apiBaseURL, queryString))
                .doOnError(error -> logger.error("MSG=execute GET request failed, action=GetApi, URL={}, queryString={}, error='{}'", apiBaseURL, queryString, error.getMessage()));
    }

    private WebClient.RequestBodySpec getRequestBodySpec(HttpMethod httpMethod, String apiBaseURL, String queryString) {
        return webClientBuilder.clone()
                .baseUrl(apiBaseURL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .method(httpMethod)
                .uri(queryString);
    }


}
