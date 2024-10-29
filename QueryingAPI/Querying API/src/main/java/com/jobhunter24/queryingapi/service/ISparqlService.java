package com.jobhunter24.queryingapi.service;

import java.util.List;
import java.util.Map;

public interface ISparqlService {
    public List<Map<String, Object>> executeSparqlQuery(String queryString);
}
