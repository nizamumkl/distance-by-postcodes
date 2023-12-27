package com.nizam.geo.aspect;

import org.aspectj.lang.annotation.*;
import org.slf4j.*;
import org.springframework.stereotype.*;

@Aspect
@Component
public class RequestLoggingAspect {

    private Logger logger = LoggerFactory.getLogger(RequestLoggingAspect.class);

    @Before("execution(* com.nizam.geo.controller.DistanceController.getDistance(..)) && args(postcode1, postcode2)")
    public void logRequest(String postcode1, String postcode2) {
        logger.info("Request received for postal codes: {} and {}", postcode1, postcode2);
    }
}
