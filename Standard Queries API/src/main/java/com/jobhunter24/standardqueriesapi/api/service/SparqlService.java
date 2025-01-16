package com.jobhunter24.standardqueriesapi.api.service;

import ch.qos.logback.classic.Logger;
import com.jobhunter24.standardqueriesapi.tools.QueryLoader;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SparqlService implements ISparqlService {
    @Value("${SPARQL_ENDPOINT}")
    private String sparqlEndpointUrl;

    @Value("${FUSEKI_USERNAME}")
    private String username;

    @Value("${FUSEKI_PASSWORD}")
    private String password;

    private final QueryLoader queryLoader;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(SparqlService.class);

    public SparqlService(QueryLoader queryLoader) {
        this.queryLoader = queryLoader;
    }

    @Override
    public List<Map<String, Object>> getEntities() {
        try (QueryExecution qexec = QueryExecutionHTTP.create()
                .endpoint(sparqlEndpointUrl)
                .query(queryLoader.getQuery("getEntities"))
                .httpHeader("Authorization", "Basic " + encodeCredentials(username, password))
                .build()
        ) {
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
    public List<Map<String, Object>> getPropertiesOf(String entityClass) {
        try (
                QueryExecution qexec = QueryExecutionHTTP.create()
                        .endpoint(sparqlEndpointUrl)
                        .query(queryLoader.getQuery("getEntityProperties").replace("<<var>>", "<"+entityClass+">"))
                        .httpHeader("Authorization", "Basic " + encodeCredentials(username, password))
                        .build()) {
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
            System.out.println(e.getMessage());
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Map<String, Object>> getInstancesOf(String entityClass) {
        try (QueryExecution qexec = QueryExecutionHTTP.create()
                .endpoint(sparqlEndpointUrl)
                .query(queryLoader.getQuery("getInstancesOf").replace("<<var>>", "<"+entityClass+">"))
                .httpHeader("Authorization", "Basic " + encodeCredentials(username, password))
                .build()) {
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
    public Map<String, Map<String, String>> getLabelsAndClasses(List<String> entityClasses) {
        // Build the entity classes filter as a comma-separated list for the VALUES clause
        StringBuilder entityFilter = new StringBuilder();
        for (int i = 0; i < entityClasses.size(); i++) {
            if (i > 0) entityFilter.append(" ");
            entityFilter.append("<").append(entityClasses.get(i)).append(">");
        }

        // Insert the entity filter into the query
        String query = queryLoader.getQuery("getLabelsAndClasses")
                .replace("<<entityClasses>>", entityFilter.toString());

        logger.info(query);

        try (QueryExecution qexec = QueryExecutionHTTP.create()
                .endpoint(sparqlEndpointUrl)
                .query(query)
                .httpHeader("Authorization", "Basic " + encodeCredentials(username, password))
                .build()) {
            // Execute the query and obtain results
            ResultSet results = qexec.execSelect();

            Map<String, Map<String, String>> resultMap = new HashMap<>();

            // Process each result from the query
            while (results.hasNext()) {
                QuerySolution solution = results.next();
                Map<String, String> rowMap = new HashMap<>();

                // Add entity URI, label, and class to the result map
                String entityUri = solution.getResource("entity").toString();
                String label = solution.contains("label") ? solution.getLiteral("label").getString() : null;
                String className = solution.getResource("class").toString();

                // Store the label and class for the entity
                rowMap.put("label", label);
                rowMap.put("class", className);

                // Add the entity URI as the key
                resultMap.put(entityUri, rowMap);
            }

            if (resultMap.isEmpty()) {
                System.out.println("No results found.");
            }

            return resultMap;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage(), e);
            return Collections.emptyMap();
        }
    }

    @Override
    public List<Map<String, Object>> getSubclassesOf(String entityClass) {
        try (QueryExecution qexec = QueryExecutionHTTP.create()
                .endpoint(sparqlEndpointUrl)
                .query(queryLoader.getQuery("getSubclassesOf").replace("<<var>>", "<"+entityClass+">"))
                .httpHeader("Authorization", "Basic " + encodeCredentials(username, password))
                .build()) {
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
    public List<Map<String, Object>> getFilteredSubclassInstancesOf(List<String> entityClasses, String searchTerm) {
        StringBuilder entityFilter = new StringBuilder();
        for (int i = 0; i < entityClasses.size(); i++) {
            if (i > 0) entityFilter.append(", \n ");
            entityFilter.append("<").append(entityClasses.get(i)).append(">");
        }


        try (QueryExecution qexec = QueryExecutionHTTP.create()
                .endpoint(sparqlEndpointUrl)
                .query(queryLoader.getQuery("getFilteredSubclassInstancesOf")
                        .replace("<<entityClasses>>", entityFilter.toString())
                        .replace("<<search>>","\""+searchTerm+"\""))
                .httpHeader("Authorization", "Basic " + encodeCredentials(username, password))
                .build()) {
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
    public List<Map<String, Object>> getAllDataOf(String entityClass) {
        try (QueryExecution qexec = QueryExecutionHTTP.create()
                .endpoint(sparqlEndpointUrl)
                .query(queryLoader.getQuery("getAllData").replace("<<var>>", "<"+entityClass+">"))
                .httpHeader("Authorization", "Basic " + encodeCredentials(username, password))
                .build()) {
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

    private String encodeCredentials(String username, String password) {
        String credentials = username + ":" + password;
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
