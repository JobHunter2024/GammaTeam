package com.jobhunter24.standardqueriesapi.api.service;

import ch.qos.logback.classic.Logger;
import com.jobhunter24.standardqueriesapi.tools.QueryLoader;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SparqlService implements ISparqlService {
    Dotenv dotenv = Dotenv.load();
    private final String sparqlEndpointUrl = dotenv.get("SPARQL_ENDPOINT");
    private final QueryLoader queryLoader;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(SparqlService.class);

    public SparqlService(QueryLoader queryLoader) {
        this.queryLoader = queryLoader;
    }

    @Override
    public List<Map<String, Object>> getEntities() {
        try (QueryExecution qexec = QueryExecutionHTTP.service(sparqlEndpointUrl, queryLoader.getQuery("getEntities"))) {
            // Execute the query and obtain results
            ResultSet results = qexec.execSelect();
            List<Map<String, Object>> resultList = new ArrayList<>();

            if (results.hasNext()) {
                // Iterate over each result
                while (results.hasNext()) {
                    QuerySolution solution = results.next();
                    Map<String, Object> rowMap = new HashMap<>();

                    // Dynamically add each variable and its value to the map
                    for (String var : results.getResultVars()) {
                        if (solution.contains(var)) {
                            rowMap.put(var, solution.get(var).toString());
                        } else {
                            rowMap.put(var, null);  // Handle optional values gracefully
                        }
                    }

                    // Add the row to the results list
                    resultList.add(rowMap);
                }
            } else {
                System.out.println("No results found.");
            }

            return resultList;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Map<String, Object>> getRelationsOf(String entityClass) {
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getPropertiesOf(String entityClass) {
        try (QueryExecution qexec = QueryExecutionHTTP.service(sparqlEndpointUrl, queryLoader.getQuery("getEntityProperties").replace("<<var>>", "<"+entityClass+">"))) {
            // Execute the query and obtain results
            ResultSet results = qexec.execSelect();
            List<Map<String, Object>> resultList = new ArrayList<>();

            if (results.hasNext()) {
                // Iterate over each result
                while (results.hasNext()) {
                    QuerySolution solution = results.next();
                    Map<String, Object> rowMap = new HashMap<>();

                    // Dynamically add each variable and its value to the map
                    for (String var : results.getResultVars()) {
                        if (solution.contains(var)) {
                            rowMap.put(var, solution.get(var).toString());
                        } else {
                            rowMap.put(var, null);  // Handle optional values gracefully
                        }
                    }

                    // Add the row to the results list
                    resultList.add(rowMap);
                }
            } else {
                System.out.println("No results found.");
            }

            return resultList;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public void getRelationsBetween() {

    }
}
