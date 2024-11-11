package com.jobhunter24.standardqueriesapi;

import com.jobhunter24.standardqueriesapi.api.service.SparqlService;
import com.jobhunter24.standardqueriesapi.tools.QueryLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class SecondIterationTddTests {
    public static final QueryLoader loader = new QueryLoader();
    public static final  SparqlService sparqlService = new SparqlService(loader);

    @Test
    public void GetEntities_None_ListOfEntities() {
        //Arrange
        try { //Should be loaded by default by Spring Boot, not working in the test environment
            loader.init();
        } catch (Exception e) {
            Assertions.fail(e);
        }

        //Act
        List<Map<String,Object>> response = sparqlService.getEntities();
        System.out.println("LoaderResponse "+loader.getQuery("getEntities"));
        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty()); //Depends if the Ontology houses any valid entities
    }

    @Test void GetPropertiesOfEntity_EntityId_ListOfProperties() {
        //Arrange
        try { //Should be loaded by default by Spring Boot, not working in the test environment
            loader.init();
        } catch (Exception e) {
            Assertions.fail(e);
        }

        String entityClassUri = "http://www.semanticweb.org/habas/ontologies/2024/9/JobHunterTest#Company";
        //Act
        List<Map<String,Object>> response = sparqlService.getPropertiesOf(entityClassUri);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }
}

