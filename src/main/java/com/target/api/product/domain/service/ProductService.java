package com.target.api.product.domain.service;

import com.target.api.product.domain.entity.Product;
import com.target.api.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Mono<Product> findProductById(String id) {
        return productRepository.findProductById(id);
    }

    public Mono<Product> save(Product product) {
        return productRepository.save(product);
    }

}
