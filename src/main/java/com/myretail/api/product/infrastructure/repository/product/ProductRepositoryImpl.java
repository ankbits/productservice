package com.myretail.api.product.infrastructure.repository.product;

import com.myretail.api.product.domain.entity.Product;
import com.myretail.api.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private static final Logger logger = Loggers.getLogger(ProductRepositoryImpl.class);

    private final ProductCBRepository productCBRepository;

    @Override
    public Mono<Product> findProductById(String id) {
        return productCBRepository.findById(id)
                .doOnNext(product -> logger.info("MSG=queryByProductId found matching Product, productId={}", id))
                .doOnError(error -> logger.error("MSG=queryByProductId repository error, productId={}, error={}", id, error.getMessage(), error));
    }

    @Override
    public Mono<Product> save(Product product) {
        return productCBRepository.save(product)
                .doOnNext(offer -> logger.info("MSG=Save Product, status=success, productId={}", product.getId()))
                .doOnError(error -> logger.error("MSG=Save Product repository error, productId={}, error: {}", product.getId(), error));
    }
}
