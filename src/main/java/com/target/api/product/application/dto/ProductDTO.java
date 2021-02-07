package com.target.api.product.application.dto;

import com.target.api.product.domain.entity.price.Price;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDTO {
    String id;
    String name;
    Price currentPrice;

}
