package com.myretail.api.product.application.common.exception;

import com.myretail.api.product.domain.common.exception.BusinessException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.util.Map;

@Component

public class GlobalErrorAttributes extends DefaultErrorAttributes {
    private static final Logger logger = Loggers.getLogger(GlobalErrorAttributes.class);

    private static final String STATUS = "status";
    private static final String MESSAGE = "message";


    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        Throwable exception = getError(webRequest);
        logger.error("MSG=Global error handler caught an exception, error={}", exception.getMessage());
        if (exception instanceof BusinessException) {
            errorAttributes.put(STATUS, ((BusinessException) exception).getStatusCode());
            errorAttributes.put(MESSAGE, exception.getMessage());
        } else {
            logger.error("MSG='Global error handler caught an unexpected exception', monitoring=true, error={}", exception.getMessage());
        }
        return errorAttributes;
    }

}
