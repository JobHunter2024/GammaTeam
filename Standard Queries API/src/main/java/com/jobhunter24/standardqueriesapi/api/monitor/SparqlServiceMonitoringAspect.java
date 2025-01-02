package com.jobhunter24.standardqueriesapi.api.monitor;

import ch.qos.logback.classic.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class SparqlServiceMonitoringAspect {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(SparqlServiceMonitoringAspect.class.getName());

    @Value("${ONTOLOGY_URI_REGEX}")
    private String ontology_uri_regex;

    private static final List<String> DISALLOWED_CHARACTERS = List.of(";", "{", "}", "\"", "'");

    // List of string argument names to be excluded from validation
    private static final List<String> exceptedStringArgs = List.of("searchTerm");

    private boolean containsDisallowedCharacters(String input) {
        for (String character : DISALLOWED_CHARACTERS) {
            if (input.contains(character)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidUri(String uri) {
        return uri.matches(ontology_uri_regex);
    }

    @Around("execution(* com.jobhunter24.standardqueriesapi.api.service.SparqlService.*(..))")
    public Object validateInputs(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        String[] parameterNames = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getParameterNames();

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            String paramName = parameterNames[i];

            if (arg instanceof String) {
                String strArg = (String) arg;

                if (!exceptedStringArgs.contains(paramName)) {
                    if (!isValidUri(strArg) || containsDisallowedCharacters(strArg)) {
                        logger.warn("Invalid String input detected: {}", strArg);
                        throw new IllegalArgumentException("Invalid String argument.");
                    }
                }

            } else if (arg instanceof List) {
                List<?> listArg = (List<?>) arg;
                for (Object element : listArg) {
                    if (element instanceof String) {
                        String strElement = (String) element;
                        if (!isValidUri(strElement) || containsDisallowedCharacters(strElement)) {
                            logger.warn("Invalid element in List input detected: {}", strElement);
                            throw new IllegalArgumentException("Invalid List element.");
                        }
                    } else {
                        logger.warn("Unsupported entity type in List: {}", element.getClass().getName());
                        throw new IllegalArgumentException("Unsupported entity type in List.");
                    }
                }
            } else {
                logger.debug("Skipping unsupported argument type: {}", arg.getClass().getName());
            }
        }

        return joinPoint.proceed();
    }
}
