package com.myretail.api.product.domain.entity;


import com.myretail.api.product.domain.entity.price.Price;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Builder
@Getter
@Setter
public class Product {
    @Id
    String id;
    Price price;

}
