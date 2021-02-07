package com.myretail.api.product.domain.repository;

import com.myretail.api.product.domain.entity.Product;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository {

    Mono<Product> findProductById(String id);
    Mono<Product> save(Product product);
}
