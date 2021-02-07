package com.myretail.api.product.application.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {


    @Value("${spring.couchbase.bucket.name}")
    private String bucketName;

    @Value("${spring.couchbase.user.name}")
    private String userName;

    @Value("${spring.couchbase.bucket.password}")
    private String bucketPassword;

    @Value("${spring.couchbase.bootstrap-host}")
    private String bootstrapHost;


    @Override
    public String getConnectionString() {
        return bootstrapHost;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return bucketPassword;
    }

    @Override
    public String getBucketName() {
        return bucketName;
    }

}
