package com.jobhunter24.standardqueriesapi.api.service;

import java.util.List;
import java.util.Map;

public interface ISparqlService {
    public List<Map<String,Object>> getEntities();
    public List<Map<String, Object>> getPropertiesOf(String entityClass);
    public List<Map<String, Object>> getInstancesOf(String entityClass);
    public Map<String, Map<String, String>> getLabelsAndClasses(List<String> entityClasses);
    public List<Map<String, Object>> getSubclassesOf(String entityClass);
    public List<Map<String, Object>> getFilteredSubclassInstancesOf(List<String> entityClasses, String searchTerm);
    public List<Map<String, Object>> getAllDataOf(String entityClass);
}
