package com.jobhunter24.queryingapi.service;

import ch.qos.logback.classic.Logger;
import com.jobhunter24.queryingapi.QueryingApiApplication;
import com.jobhunter24.queryingapi.api.model.Response;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QueryService implements IQueryService {
    private final SparqlService sparqlService;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(QueryingApiApplication.class);

    @Autowired
    public QueryService(SparqlService sparqlService) {
        this.sparqlService = sparqlService;
    }

    //@Async
    public Response executeQuery(String query) {
        Response response;
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
}
