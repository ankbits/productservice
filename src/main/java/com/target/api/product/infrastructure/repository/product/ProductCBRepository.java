package com.target.api.product.infrastructure.repository.product;

import com.target.api.product.domain.entity.Product;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCBRepository extends ReactiveCouchbaseRepository<Product, String> {


}
