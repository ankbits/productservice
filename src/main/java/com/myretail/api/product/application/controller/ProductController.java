package com.myretail.api.product.application.controller;

import com.myretail.api.product.application.dto.ProductDTO;
import com.myretail.api.product.application.transformer.ProductTransformer;
import com.myretail.api.product.domain.entity.Product;
import com.myretail.api.product.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductTransformer transformer;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductDTO>> getProduct(@PathVariable(value = "id") String productId) {

        return Mono.just(productId)
                .flatMap(productService::findProductById)
                .flatMap(transformer::transformToDTO)
                .map(productDTO -> ResponseEntity.ok().body(productDTO));
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> createProduct(@PathVariable(value = "id") String productId,
                                              @RequestBody ProductDTO productDTO) {

        return Mono.just(productId)
                //.flatMap(productService::findProductById)
                .flatMap(product -> productService.save(buildProduct(productId, productDTO)))
                .map(product -> ResponseEntity.ok(null));
    }

    private Product buildProduct(String productId, ProductDTO productDTO) {
        return Product.builder()
                .id(productId)
                .price(productDTO.getCurrentPrice())
                .build();
    }
}
