package com.myretail.api.product.infrastructure.repository.product;

import com.myretail.api.product.domain.entity.Product;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;

public interface ProductCBRepository extends ReactiveCouchbaseRepository<Product, String> {


}
