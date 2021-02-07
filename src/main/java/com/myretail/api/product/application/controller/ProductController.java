package com.myretail.api.product.application.controller;

import com.myretail.api.product.application.dto.ProductDTO;
import com.myretail.api.product.application.transformer.ProductTransformer;
import com.myretail.api.product.domain.common.exception.BusinessException;
import com.myretail.api.product.domain.entity.Product;
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

    public static final String NOT_FOUND = "Not Found";
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
                .doOnNext(responseEntity -> logger.info("step=product-request-get-completed successfully, MSG=Got Product for productId={}", productId))
                .doOnError(error -> logger.error("step=product-request-get-completed-error, MSG=Got Product for productId={}, error={}", productId, error.getMessage()))
                .switchIfEmpty(propagateProductAPIErrors(productId));
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> createProduct(@PathVariable("id") String productId,
                                              @RequestBody ProductDTO productDTO) {

        return Mono.just(productId)
                .flatMap(productService::findProductById)
                .flatMap(product -> productService.save(buildProduct(productId, productDTO)))
                .map(product -> ResponseEntity.ok(null));
    }

    private Product buildProduct(String productId, ProductDTO productDTO) {
        return Product.builder()
                .id(productId)
                .price(productDTO.getCurrentPrice())
                .build();
    }

    private <T> Mono<T> propagateProductAPIErrors(String productId) {
        return Mono.error(new BusinessException(NOT_FOUND, "Product not found with Id: " + productId, HttpStatus.NOT_FOUND.value()));
    }
}
