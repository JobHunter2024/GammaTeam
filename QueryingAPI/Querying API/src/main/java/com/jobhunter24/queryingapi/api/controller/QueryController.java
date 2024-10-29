package com.jobhunter24.queryingapi.api.controller;

import com.jobhunter24.queryingapi.api.model.Query;
import com.jobhunter24.queryingapi.api.model.Response;
import com.jobhunter24.queryingapi.service.IQueryService;
import com.jobhunter24.queryingapi.tools.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {

    private IQueryService queryService;
    private QueryBuilder queryBuilder;

    @Autowired
    public QueryController(IQueryService queryService, QueryBuilder queryBuilder) {
        this.queryService = queryService;
        this.queryBuilder = queryBuilder;

    }

    @PostMapping("/query")
    public Response postQuery(@RequestBody Query query) {
        String parsedQuery = queryBuilder.buildQuery(query);
        Response newResponse = queryService.executeQuery(parsedQuery);
        return newResponse;
    }
}
