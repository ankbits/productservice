package com.myretail.api.product.infrastructure.service.productdetails;

import com.myretail.api.product.domain.service.productdetails.ProductDetails;
import com.myretail.api.product.domain.service.productdetails.ProductDetailsService;
import com.myretail.api.product.infrastructure.service.adapter.ServiceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final ServiceAdapter serviceAdapter;
    @Value("${product.details.service.base-url}")
    private String productDetailsServiceBaseURL;

    @Override
    public Mono<ProductDetails> getProductDetails(String productId) {

        return serviceAdapter.makeGETCall(productDetailsServiceBaseURL, buildProductDetailsURI(productId), ProductDetails.class);

    }

    private String buildProductDetailsURI(String productId) {
        return UriComponentsBuilder.newInstance()
                .path("/{id}")
                .queryParam("id_type", "TCIN")
                .queryParam("fields", "description")
                .queryParam("key", "0000")
                .build(productId)
                .toString();
    }

}
