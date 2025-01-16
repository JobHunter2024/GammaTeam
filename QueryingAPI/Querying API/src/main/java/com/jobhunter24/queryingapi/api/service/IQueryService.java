package com.jobhunter24.queryingapi.api.service;

import com.jobhunter24.queryingapi.api.model.DataObject;
import com.jobhunter24.queryingapi.api.model.Response;

import java.util.List;
import java.util.Map;

public interface IQueryService {
    public Response executeQuery(String query) ;
    public List<Map<String, Object>> generateAndExecuteSparqlQuery(DataObject query);
}
