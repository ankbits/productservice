package com.target.api.product.domain.entity.price;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Price {

    private Double value;
    private CurrencyCode currencyCode;

}
