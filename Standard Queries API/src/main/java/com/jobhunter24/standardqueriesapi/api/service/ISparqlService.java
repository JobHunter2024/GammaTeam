package com.jobhunter24.standardqueriesapi.api.service;

import java.util.List;
import java.util.Map;

public interface ISparqlService {
    public List<Map<String,Object>> getEntities();
    public List<Map<String, Object>> getRelationsOf(String entityClass);
    public void getRelationsBetween();
    public List<Map<String, Object>> getPropertiesOf(String entityClass);
}
