package com.jobhunter24.standardqueriesapi.utils;

import com.jobhunter24.standardqueriesapi.api.dto.label.EntityLabelsQueryModel;
import com.jobhunter24.standardqueriesapi.api.dto.property.EntityPropertyQueryModel;
import com.jobhunter24.standardqueriesapi.api.dto.subclass.FilteredEntitySubclassQuery;

import java.util.List;
import java.util.Map;

public class Constants {
    public static final String ENTITY_CLASS_NAME = "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Entity";

    public static final List<String> CLASSES_AND_LABELS_PARAMETER = List.of(
            "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#ProgrammingLanguage",
            "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Library");

    public static final Map<String, Object> ENTITY = Map.of(
                "description", "Entity Example",
                "label", "Entity Example",
                "class", "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Entity"
            );

    public static final Map<String, Object> PROPERTY = Map.of(
            "propertyLabel" , "Example Property",
            "dataType","http://www.w3.org/2001/XMLSchema#string",
            "property","http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#exampleProperty"
    );

    public static final EntityPropertyQueryModel ENTITY_PROPERTY_QUERY_MODEL
            = new EntityPropertyQueryModel("http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Entity");

    public static final EntityLabelsQueryModel ENTITY_LABELS_QUERY_MODEL
            = new EntityLabelsQueryModel(List.of("http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Entity", "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Entity1"));

    public static final FilteredEntitySubclassQuery FILTERED_ENTITY_SUBCLASS_QUERY
            = new FilteredEntitySubclassQuery(List.of("http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Entity","http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#OtherEntity"),"");

    public static final Map<String, Object> LABEL = Map.of(
            "label", "Example Label Response",
            "class", "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Entity"
    );

    public static final Map<String, Object> SUBCLASS = Map.of(
            "subclass","http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#ExampleSubclass",
            "label","Example Subclass"
    );

    public static final Map<String, Object> FILTERED_SUBCLASS = Map.of(
            "subclass","http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#ExampleSubclass",
            "description", "Example Subclass Description",
            "label","Example Subclass"
    );
}
