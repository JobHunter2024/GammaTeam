package com.jobhunter24.queryingapi.service;

import ch.qos.logback.classic.Logger;
import com.jobhunter24.queryingapi.QueryingApiApplication;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SparqlService {
    Dotenv dotenv = Dotenv.load();
    private final String sparqlEndpointUrl = dotenv.get("SPARQL_ENDPOINT"); // Replace with your SPARQL endpoint URL

    private static final Logger logger = (Logger) LoggerFactory.getLogger(QueryingApiApplication.class);

    public List<Map<String, Object>> executeSparqlQuery(String queryString) {

        QueryExecution qexec = QueryExecutionHTTP.service(sparqlEndpointUrl, queryString);
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
}
