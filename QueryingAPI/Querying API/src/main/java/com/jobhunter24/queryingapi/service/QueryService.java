package com.jobhunter24.queryingapi.service;

import ch.qos.logback.classic.Logger;
import com.jobhunter24.queryingapi.QueryingApiApplication;
import com.jobhunter24.queryingapi.api.model.DataObject;
import com.jobhunter24.queryingapi.api.model.Response;
import com.jobhunter24.queryingapi.validators.SparqlQueryValidator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QueryService implements IQueryService {
    private final SparqlService sparqlService;
    private final SparqlQueryValidator sparqlQueryValidator;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(QueryingApiApplication.class);

    @Autowired
    public QueryService(SparqlService sparqlService, SparqlQueryValidator sparqlQueryValidator) {
        this.sparqlService = sparqlService;
        this.sparqlQueryValidator = sparqlQueryValidator;
    }

    //@Async
    public Response executeQuery(String query) {
        Response response;

        if (!SparqlQueryValidator.isValidSparqlQuery(query).success) {
            response = Response.builder()
                    .resultList(new ArrayList<>())
                    .success(false)
                    .message("Invalid query")
                    .build();
            return response;
        }

        try {
            List<Map<String, Object>> resultList = sparqlService.executeSparqlQuery(query);
            response = Response.builder()
                    .resultList(resultList)
                    .success(true)
                    .message("Query executed successfully").build();
        } catch (Exception e) {
            response = Response.builder()
                    .message(e.getMessage())
                    .success(false)
                    .resultList(new ArrayList<>()).build();

            logger.error(e.getMessage(), e);
        }
        return response;
    }

    @Override
    public List<Map<String, Object>> generateAndExecuteSparqlQuery(DataObject query) {
        return List.of(Map.of("value", query.subject));
    }
}
