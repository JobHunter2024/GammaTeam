package com.jobhunter24.queryingapi;

import com.jobhunter24.queryingapi.api.model.Query;
import com.jobhunter24.queryingapi.api.model.Relation;
import com.jobhunter24.queryingapi.api.model.Response;
import com.jobhunter24.queryingapi.service.QueryService;
import com.jobhunter24.queryingapi.service.SparqlService;
import com.jobhunter24.queryingapi.tools.QueryBuilder;
import com.jobhunter24.queryingapi.validators.SparqlQueryValidator;
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
                .query("PREFIX owl: <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> SELECT DISTINCT ?class ?label ?description WHERE {    ?class a owl:Class .    OPTIONAL { ?class rdfs:label ?label }\n    OPTIONAL { ?class rdfs:comment ?description }\n} \nLIMIT 25")
                .relations(Arrays.asList(relation,relation)).build();
        //Act
        String response = qb.buildQuery(query);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertTrue(SparqlQueryValidator.isValidSparqlQuery(response).success);
    }

    @Test
    public void ExecuteQuery_QueryAsString_Response() {
        //Arrange
        SparqlService sparqlService = new SparqlService();
        SparqlQueryValidator sparqlQueryValidator = new SparqlQueryValidator();
        QueryService qs = new QueryService(sparqlService, sparqlQueryValidator);
        String query = "PREFIX owl: <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> SELECT DISTINCT ?class ?label ?description WHERE {    ?class a owl:Class .    OPTIONAL { ?class rdfs:label ?label }\n    OPTIONAL { ?class rdfs:comment ?description }\n} \nLIMIT 25";

        //Act
        Response response = qs.executeQuery(query);
        System.out.println(response.message);

        //Assert

        Assertions.assertTrue(response.success);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.message);

    }

    @Test
    public void QueryValidator_QueryAsString_BaseResponse() {
        String query = "PREFIX owl: <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> SELECT DISTINCT ?class ?label ?description WHERE {    ?class a owl:Class .    OPTIONAL { ?class rdfs:label ?label }\n    OPTIONAL { ?class rdfs:comment ?description }\n} \nLIMIT 25";

        Assertions.assertTrue(SparqlQueryValidator.isValidSparqlQuery(query).success);
    }
}