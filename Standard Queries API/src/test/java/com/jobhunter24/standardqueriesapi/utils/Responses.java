package com.jobhunter24.standardqueriesapi.utils;

import java.util.List;
import java.util.Map;

import static com.jobhunter24.standardqueriesapi.utils.Constants.*;

public class Responses {
    public static List<Map<String, Object>> getEntitiesList() {
        return List.of(
                ENTITY, ENTITY
        );
    }

    public static List<Map<String, Object>> getPropertiesList() {
        return List.of(
                PROPERTY, PROPERTY
        );
    }

    public static Map<String, Map<String, Object>> getLabels() {
        return Map.of(
                "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#ExampleEntity", LABEL,
                "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#ExampleEntity1", LABEL
        );
    }

    public static List<Map<String, Object>> getSubclassesList() {
        return List.of(
                SUBCLASS, SUBCLASS
        );
    }
}
