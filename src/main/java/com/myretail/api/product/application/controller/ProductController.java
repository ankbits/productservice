package com.myretail.api.product.application.controller;

import com.myretail.api.product.application.dto.ProductDTO;
import com.myretail.api.product.application.transformer.ProductTransformer;
import com.myretail.api.product.domain.common.exception.BusinessException;
import com.myretail.api.product.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    public static final String ERROR_CODE_NOT_FOUND = "Not Found";
    private static final Logger logger = Loggers.getLogger(ProductController.class);
    private final ProductService productService;
    private final ProductTransformer transformer;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductDTO>> getProduct(@PathVariable("id") @NonNull String productId) {

        return Mono.just(productId)
                .doOnNext(id -> logger.info("step=product-request-get-started, MSG=GET Product by Id, productId={}", productId))
                .flatMap(productService::findProductById)
                .flatMap(transformer::transformToDTO)
                .map(productDTO -> ResponseEntity.ok().body(productDTO))
                .doOnNext(responseEntity -> logger.info("step=product-request-get-completed successfully, MSG=Got product for productId={}", productId))
                .doOnError(error -> logger.error("step=product-request-get-error, MSG=Got Error while fetching product with productId={}, error={}", productId, error.getMessage()))
                .switchIfEmpty(propagateProductAPIErrors(productId));
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> createProduct(@PathVariable("id") @NonNull String productId,
                                                      @RequestBody ProductDTO productDTO) {

        return Mono.just(productDTO)
                .flatMap(transformer::transformToCore)
                .flatMap(productService::save)
                .map(product -> ResponseEntity.ok().build())
                .doOnNext(product -> logger.info("step=product-request-create-completed successfully, MSG=PUT product Create/Update Product, productId={}", productId))
                .doOnError(error -> logger.error("step=product-request-create-error, MSG=PUT product error , productId={}, error={}", productId, error.getMessage()));
    }


    private <T> Mono<T> propagateProductAPIErrors(String productId) {
        return Mono.error(new BusinessException(ERROR_CODE_NOT_FOUND, "Product not found with productId: " + productId, HttpStatus.NOT_FOUND.value()));
    }
}
