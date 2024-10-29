package com.jobhunter24.queryingapi;

import com.jobhunter24.queryingapi.api.model.Query;
import com.jobhunter24.queryingapi.api.model.Relation;
import com.jobhunter24.queryingapi.api.model.Response;
import com.jobhunter24.queryingapi.service.QueryService;
import com.jobhunter24.queryingapi.service.SparqlService;
import com.jobhunter24.queryingapi.tools.QueryBuilder;
import com.jobhunter24.queryingapi.validators.SparqlQueryValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class FirstIterationTddTests {
    @Test
    public void BuildQuery_Query_QueryAsString() {
        //Arrange
        QueryBuilder qb = new QueryBuilder();
        Relation relation = Relation.builder()
                .subject("test")
                .predicate("test")
                .object("test").build();

        Query query = Query.builder()
                .query("query example")
                .relations(Arrays.asList(relation,relation)).build();
        //Act
        String response = qb.buildQuery(query);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertTrue(SparqlQueryValidator.isValidSparqlQuery(response));
    }

    @Test
    public void ExecuteQuery_QueryAsString_Response() {
        //Arrange
        SparqlService sparqlService = new SparqlService();
        QueryService qs = new QueryService(sparqlService);
        String query = "query example";

        //Act
        Response response = qs.executeQuery(query);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.success);
        Assertions.assertNotNull(response.message);
    }

    @Test
    public void LoggingTest() {
        Logger logger = LogManager.getLogger("Testing");
        logger.info("Testing From QueryingAPI");

        Assertions.assertTrue(true);
    }
}