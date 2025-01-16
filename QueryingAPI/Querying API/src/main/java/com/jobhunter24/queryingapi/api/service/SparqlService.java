package com.jobhunter24.queryingapi.api.service;

import ch.qos.logback.classic.Logger;
import com.jobhunter24.queryingapi.QueryingApiApplication;
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
    private String sparqlEndpointUrl; // Replace with your SPARQL endpoint URL

    @Value("${FUSEKI_USERNAME}")
    private String username;

    @Value("${FUSEKI_PASSWORD}")
    private String password;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(QueryingApiApplication.class);

    public List<Map<String, Object>> executeSparqlQuery(String queryString) {

        QueryExecution qexec = QueryExecutionHTTP.create()
                .endpoint(sparqlEndpointUrl)
                .query(queryString)
                .httpHeader("Authorization", "Basic " + encodeCredentials(username, password))
                .build();
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
    }

    private String encodeCredentials(String username, String password) {
        String credentials = username + ":" + password;
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
