package com.jobhunter24.queryingapi.monitor;

import ch.qos.logback.classic.Logger;
import com.jobhunter24.queryingapi.api.model.DataObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Aspect
@Component
public class QueryingMonitor {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(QueryingMonitor.class.getName());

    private static final String PREFIX_1 = "http://www.semanticweb.org/ana/ontologies/2024/10/";
    private static final String PREFIX_2 = "http://www.w3.org/";

    @Around("execution(* com.jobhunter24.queryingapi.util.QueryBuilder.buildQuery(..))")
    public Object validateUris(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        if (args.length > 0 && args[0] instanceof DataObject) {
            DataObject dataObject = (DataObject) args[0];

            logger.info("Validating URIs in the provided DataObject: {}", dataObject);

            // Validate top-level dataProperties
            if (dataObject.getDataProperties() != null) {
                validateKeys(dataObject.getDataProperties());
            }

            // Validate top-level objectProperties recursively
            if (dataObject.getObjectProperties() != null) {
                validateObjectProperties(dataObject.getObjectProperties());
            }
        }

        // Proceed with the method execution if all validations pass
        return joinPoint.proceed();
    }

    private void validateKeys(Map<String, ?> map) {
        for (String key : map.keySet()) {
            if (!key.startsWith(PREFIX_1) && !key.startsWith(PREFIX_2)) {
                logger.error("Invalid URI found in dataProperties: {}", key);
                throw new IllegalArgumentException("Invalid URI: " + key +
                        ". URIs must start with " + PREFIX_1 + " or " + PREFIX_2);
            }

            // If the value is a nested objectProperties map, validate recursively
            Object value = map.get(key);
            if (value instanceof Map) {
                validateKeys((Map<String, ?>) value);
            }
        }
    }

    private void validateObjectProperties(Map<String, Object> objectProperties) {
        for (Map.Entry<String, Object> entry : objectProperties.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (!key.startsWith(PREFIX_1) && !key.startsWith(PREFIX_2)) {
                logger.error("Invalid URI found in objectProperties: {}", key);
                throw new IllegalArgumentException("Invalid URI: " + key +
                        ". URIs must start with " + PREFIX_1 + " or " + PREFIX_2);
            }

            if (value instanceof Map) {
                Map<String, Object> nestedObject = (Map<String, Object>) value;

                // Validate the nested object follows the DataObject structure
                validateNestedObject(nestedObject);

                // Recursively validate nested objectProperties
                if (nestedObject.containsKey("objectProperties")) {
                    Object nestedObjectProperties = nestedObject.get("objectProperties");
                    if (nestedObjectProperties instanceof Map) {
                        validateObjectProperties((Map<String, Object>) nestedObjectProperties);
                    }
                }
            } else {
                logger.error("Invalid structure found in objectProperties: {}", value);
                throw new IllegalArgumentException("Invalid structure in objectProperties: " +
                        "Expected nested object to be a Map<String, Object> but got " + value.getClass().getSimpleName());
            }
        }
    }

    private void validateNestedObject(Map<String, Object> nestedObject) {
        if (!nestedObject.containsKey("subject") || !(nestedObject.get("subject") instanceof String)) {
            logger.error("Nested object is missing a valid 'subject' key: {}", nestedObject);
            throw new IllegalArgumentException("Nested object is missing a valid 'subject' key.");
        }

        if (!nestedObject.containsKey("instances") || !(nestedObject.get("instances") instanceof List)) {
            logger.error("Nested object is missing a valid 'instances' key: {}", nestedObject);
            throw new IllegalArgumentException("Nested object is missing a valid 'instances' key.");
        }

        if (!nestedObject.containsKey("dataProperties") || !(nestedObject.get("dataProperties") instanceof Map)) {
            logger.error("Nested object is missing a valid 'dataProperties' key: {}", nestedObject);
            throw new IllegalArgumentException("Nested object is missing a valid 'dataProperties' key.");
        }

        if (!nestedObject.containsKey("objectProperties") || !(nestedObject.get("objectProperties") instanceof Map)) {
            logger.error("Nested object is missing a valid 'objectProperties' key: {}", nestedObject);
            throw new IllegalArgumentException("Nested object is missing a valid 'objectProperties' key.");
        }

        // Validate keys in dataProperties
        validateKeys((Map<String, Object>) nestedObject.get("dataProperties"));
    }
}
