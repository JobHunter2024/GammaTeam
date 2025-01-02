package com.jobhunter24.queryingapi.api.controller;

import com.jobhunter24.queryingapi.api.model.DataObject;
import com.jobhunter24.queryingapi.api.model.Query;
import com.jobhunter24.queryingapi.api.model.Response;
import com.jobhunter24.queryingapi.service.IQueryService;
import com.jobhunter24.queryingapi.service.ISparqlService;
import com.jobhunter24.queryingapi.util.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class QueryController {

    private final IQueryService queryService;
    private final ISparqlService sparqlService;
    private final QueryBuilder queryBuilder = new QueryBuilder();

    @Autowired
    public QueryController(IQueryService queryService, ISparqlService sparqlService) {
        this.queryService = queryService;
        this.sparqlService = sparqlService;
    }

    @PostMapping("/query")
    public Response postQuery(@RequestBody Query query) {
        Response newResponse = queryService.executeQuery(query.query);
        return newResponse;
    }

    @PostMapping("/query/generate")
    public ResponseEntity<List<Map<String, Object>>> postGenerateAndExecuteQuery(@RequestBody DataObject query) {
        String parsedQuery = queryBuilder.generateSparqlQuery(query);
        var response = sparqlService.executeSparqlQuery(parsedQuery);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
