package com.myretail.api.product.domain.service.productdetails;

import reactor.core.publisher.Mono;

public interface ProductDetailsService {
    Mono<ProductDetails> getProductDetails(String productId);
}
