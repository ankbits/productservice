package com.myretail.api.product.application.transformer;

import com.myretail.api.product.application.dto.ProductDTO;
import com.myretail.api.product.domain.entity.Product;
import com.myretail.api.product.domain.service.productdetails.ProductDetails;
import com.myretail.api.product.domain.service.productdetails.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductTransformer {

    private final ProductDetailsService productDetailsService;

    public Mono<ProductDTO> transformToDTO(Product product) {
        return Mono.just(product)
                .flatMap(prod -> productDetailsService.getProductDetails(product.getId()))
                .map(productDetails -> buildDTO(product, productDetails));

    }

    public Mono<Product> transformToCore(ProductDTO productDTO) {
        return Mono.just(productDTO)
                .map(this::buildProduct);
    }

    private ProductDTO buildDTO(Product product, ProductDetails productDetails) {
        return ProductDTO.builder()
                .id(product.getId())
                .currentPrice(product.getPrice())
                .name(productDetails.getDescription())
                .build();
    }

    private Product buildProduct(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .price(productDTO.getCurrentPrice())
                .build();
    }

}
