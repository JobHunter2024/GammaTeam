package com.jobhunter24.standardqueriesapi.service;

import com.jobhunter24.standardqueriesapi.api.service.SparqlService;
import com.jobhunter24.standardqueriesapi.tools.QueryLoader;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.jobhunter24.standardqueriesapi.utils.Constants.*;
import static com.jobhunter24.standardqueriesapi.utils.SparqlConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SparqlServiceTests {
    @Mock
    Logger logger = LoggerFactory.getLogger("TestLogger");

    @Mock
    QueryLoader queryLoader;

    @InjectMocks
    SparqlService sparqlService;

    private QueryExecutionHTTP queryExecutionHTTP;

    @BeforeEach
    void setUp() {
        queryExecutionHTTP = mock(QueryExecutionHTTP.class);
    }

    @Test
    public void givenValidQuery_whenGetEntities_thenReturnListMap() throws Exception {
        // Stub the execSelect() method to return the MOCK_RESULT_SET
        when(queryExecutionHTTP.execSelect()).thenReturn(MOCK_RESULT_SET);

        // Stub queryLoader.getQuery() to return a valid SPARQL query string
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getEntities"));

        // Use MockedStatic to mock the static service() method
        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            // Verify the mocked QueryExecutionHTTP returns MOCK_RESULT_SET
            assertEquals(MOCK_RESULT_SET, queryExecutionHTTP.execSelect(), "The mocked execSelect should return MOCK_RESULT_SET");

            // Call the service method
            List<Map<String, Object>> entities = sparqlService.getEntities();

            // Print and validate results
            System.out.println("ENTITIES: " + entities);

            assertFalse(entities.isEmpty(), "The entities list should not be empty");
            assertEquals(10, entities.size(), "The entities list should contain 2 elements");
        }
    }

    @Test
    public void givenValidQueryButNoResults_whenGetEntities_thenReturnEmptyListMap() throws Exception {
        // Stub the execSelect() method to return the MOCK_RESULT_SET
        when(queryExecutionHTTP.execSelect()).thenReturn(MOCK_EMPTY_RESULT_SET);

        // Stub queryLoader.getQuery() to return a valid SPARQL query string
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getEntities"));

        // Use MockedStatic to mock the static service() method
        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            // Verify the mocked QueryExecutionHTTP returns MOCK_RESULT_SET
            assertEquals(MOCK_EMPTY_RESULT_SET, queryExecutionHTTP.execSelect(), "The mocked execSelect should return MOCK_RESULT_SET");

            // Call the service method
            List<Map<String, Object>> entities = sparqlService.getEntities();

            assertTrue(entities.isEmpty(), "The entities list should be empty");
        }
    }

    @Test
    public void givenInvalidQuery_whenGetEntities_thenReturnEmptyListMap() throws Exception {
        doThrow(IllegalArgumentException.class).when(queryExecutionHTTP).execSelect();
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getEntities"));

        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            // Call the service method
            List<Map<String, Object>> entities = sparqlService.getEntities();

            assertTrue(entities.isEmpty(), "The entities list should be empty");
        }
    }

    @Test
    public void givenValidQuery_whenGetProperties_thenReturnListMap() throws Exception {
        // Stub the execSelect() method to return the MOCK_RESULT_SET
        when(queryExecutionHTTP.execSelect()).thenReturn(MOCK_PROPERTIES_RESULT_SET);

        // Stub queryLoader.getQuery() to return a valid SPARQL query string
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getEntityProperties"));

        // Use MockedStatic to mock the static service() method
        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            // Verify the mocked QueryExecutionHTTP returns MOCK_RESULT_SET
            assertEquals(MOCK_PROPERTIES_RESULT_SET, queryExecutionHTTP.execSelect(), "The mocked execSelect should return MOCK_PROPERTIES_RESULT_SET");

            // Call the service method
            List<Map<String, Object>> entities = sparqlService.getPropertiesOf(ENTITY_CLASS_NAME);

            assertFalse(entities.isEmpty(), "The entities list should not be empty");
            assertEquals(10, entities.size(), "The entities list should contain 10 elements");
        }
    }

    @Test
    public void givenValidQueryButNoResults_whenGetProperties_thenReturnEmptyListMap() throws Exception {
        // Stub the execSelect() method to return the MOCK_RESULT_SET
        when(queryExecutionHTTP.execSelect()).thenReturn(MOCK_EMPTY_RESULT_SET);

        // Stub queryLoader.getQuery() to return a valid SPARQL query string
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getEntityProperties"));

        // Use MockedStatic to mock the static service() method
        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            // Verify the mocked QueryExecutionHTTP returns MOCK_RESULT_SET
            assertEquals(MOCK_EMPTY_RESULT_SET, queryExecutionHTTP.execSelect(), "The mocked execSelect should return MOCK_PROPERTIES_RESULT_SET");

            // Call the service method
            List<Map<String, Object>> entities = sparqlService.getPropertiesOf(ENTITY_CLASS_NAME);

            assertTrue(entities.isEmpty(), "The entities list should be empty");
        }
    }

    @Test
    public void givenInvalidQuery_whenGetProperties_thenReturnEmptyListMap() throws Exception {
        doThrow(IllegalArgumentException.class).when(queryExecutionHTTP).execSelect();
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getEntityProperties"));

        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            // Call the service method
            List<Map<String, Object>> entities = sparqlService.getPropertiesOf(ENTITY_CLASS_NAME);

            assertTrue(entities.isEmpty(), "The entities list should be empty");
        }
    }

    @Test
    public void givenValidQuery_whenGetInstances_thenReturnListMap() throws Exception {
        // Stub the execSelect() method to return the MOCK_RESULT_SET
        when(queryExecutionHTTP.execSelect()).thenReturn(MOCK_INSTANCES_RESULT_SET);

        // Stub queryLoader.getQuery() to return a valid SPARQL query string
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getInstancesOf"));

        // Use MockedStatic to mock the static service() method
        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            // Verify the mocked QueryExecutionHTTP returns MOCK_RESULT_SET
            assertEquals(MOCK_INSTANCES_RESULT_SET, queryExecutionHTTP.execSelect(), "The mocked execSelect should return MOCK_PROPERTIES_RESULT_SET");

            // Call the service method
            List<Map<String, Object>> entities = sparqlService.getInstancesOf(ENTITY_CLASS_NAME);

            assertFalse(entities.isEmpty(), "The entities list should not be empty");
            assertEquals(2, entities.size(), "The entities list should contain 2 elements");
        }
    }

    @Test
    public void givenValidQueryButNoResults_whenGetInstances_thenReturnEmptyListMap() throws Exception {
        // Stub the execSelect() method to return the MOCK_RESULT_SET
        when(queryExecutionHTTP.execSelect()).thenReturn(MOCK_EMPTY_RESULT_SET);

        // Stub queryLoader.getQuery() to return a valid SPARQL query string
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getInstancesOf"));

        // Use MockedStatic to mock the static service() method
        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            // Verify the mocked QueryExecutionHTTP returns MOCK_RESULT_SET
            assertEquals(MOCK_EMPTY_RESULT_SET, queryExecutionHTTP.execSelect(), "The mocked execSelect should return MOCK_PROPERTIES_RESULT_SET");

            // Call the service method
            List<Map<String, Object>> entities = sparqlService.getInstancesOf(ENTITY_CLASS_NAME);

            assertTrue(entities.isEmpty(), "The entities list should be empty");
        }
    }

    @Test
    public void givenInvalidQuery_whenGetInstance_thenReturnEmptyListMap() throws Exception {
        doThrow(IllegalArgumentException.class).when(queryExecutionHTTP).execSelect();
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getInstancesOf"));

        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            // Call the service method
            List<Map<String, Object>> entities = sparqlService.getInstancesOf(ENTITY_CLASS_NAME);

            assertTrue(entities.isEmpty(), "The entities list should be empty");
        }
    }

    @Test
    public void givenValidQuery_whenGetSubclasses_thenReturnListMap() throws Exception {
        // Stub the execSelect() method to return the MOCK_RESULT_SET
        when(queryExecutionHTTP.execSelect()).thenReturn(MOCK_SUBCLASSES_RESULT_SET);

        // Stub queryLoader.getQuery() to return a valid SPARQL query string
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getSubclassesOf"));

        // Use MockedStatic to mock the static service() method
        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            // Verify the mocked QueryExecutionHTTP returns MOCK_RESULT_SET
            assertEquals(MOCK_SUBCLASSES_RESULT_SET, queryExecutionHTTP.execSelect(), "The mocked execSelect should return MOCK_PROPERTIES_RESULT_SET");

            // Call the service method
            List<Map<String, Object>> entities = sparqlService.getSubclassesOf(ENTITY_CLASS_NAME);

            assertFalse(entities.isEmpty(), "The entities list should not be empty");
            assertEquals(7, entities.size(), "The entities list should contain 2 elements");
        }
    }

    @Test
    public void givenValidQueryButNoResults_whenGetSubclasses_thenReturnEmptyListMap() throws Exception {
        // Stub the execSelect() method to return the MOCK_RESULT_SET
        when(queryExecutionHTTP.execSelect()).thenReturn(MOCK_EMPTY_RESULT_SET);

        // Stub queryLoader.getQuery() to return a valid SPARQL query string
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getSubclassesOf"));

        // Use MockedStatic to mock the static service() method
        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            // Verify the mocked QueryExecutionHTTP returns MOCK_RESULT_SET
            assertEquals(MOCK_EMPTY_RESULT_SET, queryExecutionHTTP.execSelect(), "The mocked execSelect should return MOCK_PROPERTIES_RESULT_SET");

            // Call the service method
            List<Map<String, Object>> entities = sparqlService.getSubclassesOf(ENTITY_CLASS_NAME);

            assertTrue(entities.isEmpty(), "The entities list should be empty");
        }
    }

    @Test
    public void givenInvalidQuery_whenGetSubclasses_thenReturnEmptyListMap() throws Exception {
        doThrow(IllegalArgumentException.class).when(queryExecutionHTTP).execSelect();
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getSubclassesOf"));

        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            List<Map<String, Object>> entities = sparqlService.getSubclassesOf(ENTITY_CLASS_NAME);

            assertTrue(entities.isEmpty(), "The entities list should be empty");
        }
    }

    @Test
    public void givenValidQuery_whenGetFilteredSubclassInstances_thenReturnListMap() throws Exception {
        // Stub the execSelect() method to return the MOCK_RESULT_SET
        when(queryExecutionHTTP.execSelect()).thenReturn(MOCK_FILTERED_SUBCLASS_INSTANCES_RESULT_SET);

        // Stub queryLoader.getQuery() to return a valid SPARQL query string
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getFilteredSubclassInstancesOf"));

        // Use MockedStatic to mock the static service() method
        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            // Verify the mocked QueryExecutionHTTP returns MOCK_RESULT_SET
            assertEquals(MOCK_FILTERED_SUBCLASS_INSTANCES_RESULT_SET, queryExecutionHTTP.execSelect(), "The mocked execSelect should return MOCK_PROPERTIES_RESULT_SET");

            // Call the service method
            List<Map<String, Object>> entities = sparqlService.getFilteredSubclassInstancesOf(List.of(ENTITY_CLASS_NAME), "");

            assertFalse(entities.isEmpty(), "The entities list should not be empty");
            assertEquals(2, entities.size(), "The entities list should contain 2 elements");
        }
    }

    @Test
    public void givenValidQueryButNoResults_whenGetFilteredSubclassInstances_thenReturnEmptyListMap() throws Exception {
        // Stub the execSelect() method to return the MOCK_RESULT_SET
        when(queryExecutionHTTP.execSelect()).thenReturn(MOCK_EMPTY_RESULT_SET);

        // Stub queryLoader.getQuery() to return a valid SPARQL query string
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getFilteredSubclassInstancesOf"));

        // Use MockedStatic to mock the static service() method
        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            // Verify the mocked QueryExecutionHTTP returns MOCK_RESULT_SET
            assertEquals(MOCK_EMPTY_RESULT_SET, queryExecutionHTTP.execSelect(), "The mocked execSelect should return MOCK_PROPERTIES_RESULT_SET");

            // Call the service method
            List<Map<String, Object>> entities = sparqlService.getFilteredSubclassInstancesOf(List.of(ENTITY_CLASS_NAME), "");

            assertTrue(entities.isEmpty(), "The entities list should be empty");
        }
    }

    @Test
    public void givenInvalidQuery_whenGetSubclassInstances_thenReturnEmptyListMap() throws Exception {
        doThrow(IllegalArgumentException.class).when(queryExecutionHTTP).execSelect();
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getFilteredSubclassInstancesOf"));

        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            List<Map<String, Object>> entities = sparqlService.getFilteredSubclassInstancesOf(List.of(ENTITY_CLASS_NAME), "");

            assertTrue(entities.isEmpty(), "The entities list should be empty");
        }
    }

    @Test
    public void givenValidQuery_whenGetLabelsAndClasses_thenReturnListMap() throws Exception {
        when(queryExecutionHTTP.execSelect()).thenReturn(MOCK_LABELS_AND_CLASS_RESULT_SET);
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getLabelsAndClasses"));

        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            Map<String, Map<String, String>> entities = sparqlService.getLabelsAndClasses(CLASSES_AND_LABELS_PARAMETER);

            assertEquals(MOCK_LABELS_AND_CLASS_RESULT_SET, queryExecutionHTTP.execSelect(), "The mocked execSelect should return MOCK_LABELS_AND_CLASS_RESULT_SET");
            assertFalse(entities.isEmpty(), "The entities list should not be empty");
            assertEquals(2, entities.size(), "The entities list should contain 2 elements");
        }
    }

    @Test
    public void givenValidQueryButNoResults_whenGetLabelsAndClasses_thenReturnEmptyListMap() throws Exception {
        when(queryExecutionHTTP.execSelect()).thenReturn(MOCK_EMPTY_RESULT_SET);
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getLabelsAndClasses"));

        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            assertEquals(MOCK_EMPTY_RESULT_SET, queryExecutionHTTP.execSelect(), "The mocked execSelect should return MOCK_PROPERTIES_RESULT_SET");

            Map<String, Map<String, String>> entities = sparqlService.getLabelsAndClasses(CLASSES_AND_LABELS_PARAMETER);

            assertTrue(entities.isEmpty(), "The entities list should be empty");
        }
    }

    @Test
    public void givenInvalidQuery_whenGetClassesAndLabels_thenReturnEmptyListMap() throws Exception {
        doThrow(IllegalArgumentException.class).when(queryExecutionHTTP).execSelect();
        doReturn("SPARQL Query String").when(queryLoader).getQuery(eq("getLabelsAndClasses"));

        try (MockedStatic<QueryExecutionHTTP> mockedStatic = mockStatic(QueryExecutionHTTP.class)) {
            mockedStatic.when(() -> QueryExecutionHTTP.service(any(), any(String.class)))
                    .thenReturn(queryExecutionHTTP);

            Map<String, Map<String, String>> entities = sparqlService.getLabelsAndClasses(CLASSES_AND_LABELS_PARAMETER);

            assertTrue(entities.isEmpty(), "The entities list should be empty");
        }
    }
}
