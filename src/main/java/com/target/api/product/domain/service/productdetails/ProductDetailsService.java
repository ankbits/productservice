package com.target.api.product.domain.service.productdetails;

import reactor.core.publisher.Mono;

public interface ProductDetailsService {
    Mono<ProductDetails> getProductDetails(String productId);
}
