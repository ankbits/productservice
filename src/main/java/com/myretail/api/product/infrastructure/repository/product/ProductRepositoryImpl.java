package com.myretail.api.product.infrastructure.repository.product;

import com.myretail.api.product.domain.entity.Product;
import com.myretail.api.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductCBRepository productCBRepository;

    @Override
    public Mono<Product> findProductById(String id) {
        return productCBRepository.findById(id);
    }

    @Override
    public Mono<Product> save(Product product) {
        return productCBRepository.save(product);
    }
}
