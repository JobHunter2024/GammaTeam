package com.jobhunter24.standardqueriesapi.utils;

import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;

import java.io.ByteArrayInputStream;

public class SparqlConstants {
    public static final ResultSet MOCK_RESULT_SET;
    public static final ResultSet MOCK_EMPTY_RESULT_SET;
    public static final ResultSet MOCK_PROPERTIES_RESULT_SET;
    public static final ResultSet MOCK_INSTANCES_RESULT_SET;
    public static final ResultSet MOCK_SUBCLASSES_RESULT_SET;
    public static final ResultSet MOCK_FILTERED_SUBCLASS_INSTANCES_RESULT_SET;
    public static final ResultSet MOCK_LABELS_AND_CLASS_RESULT_SET;

    static {

        String sparqlResultJson = """
                { "head": {
                            "vars": [ "class" , "label" , "description" ]
                          } ,
                          "results": {
                            "bindings": [
                              {\s
                                "class": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Library" } ,
                                "label": { "type": "literal" , "value": "Library" }
                              } ,
                              {\s
                                "class": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Job" } ,
                                "label": { "type": "literal" , "value": "Job" }
                              } ,
                              {\s
                                "class": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#TechnicalSkill" } ,
                                "label": { "type": "literal" , "value": "Technical Skill" }
                              } ,
                              {\s
                                "class": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#SoftSkill" } ,
                                "label": { "type": "literal" , "value": "Soft Skill" }
                              } ,
                              {\s
                                "class": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Framework" } ,
                                "label": { "type": "literal" , "value": "Framework" }
                              } ,
                              {\s
                                "class": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Skill" } ,
                                "label": { "type": "literal" , "value": "Skill" }
                              } ,
                              {\s
                                "class": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Company" } ,
                                "label": { "type": "literal" , "value": "Company" }
                              } ,
                              {\s
                                "class": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#LanguageSkill" } ,
                                "label": { "type": "literal" , "value": "Language Skill" }
                              } ,
                              {\s
                                "class": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Education" } ,
                                "label": { "type": "literal" , "value": "Education" }
                              } ,
                              {\s
                                "class": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#ProgrammingLanguage" } ,
                                "label": { "type": "literal" , "value": "Programming Language" }
                              }
                            ]
                          }
                        }
        """;
// Convert the JSON string to a ByteArrayInputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(sparqlResultJson.getBytes());

        // Use ResultSetFactory to create ResultSet from the InputStream
        MOCK_RESULT_SET = ResultSetFactory.fromJSON(inputStream);
    }

    static {

        String sparqlResultJson = """
                { "head": {
                                     "vars": [ "subclass" , "label" , "description" ]
                                   } ,
                                   "results": {
                                     "bindings": [
                                      \s
                                     ]
                                   }
                                 }
        """;
// Convert the JSON string to a ByteArrayInputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(sparqlResultJson.getBytes());

        // Use ResultSetFactory to create ResultSet from the InputStream
        MOCK_EMPTY_RESULT_SET = ResultSetFactory.fromJSON(inputStream);
    }

    static {

        String sparqlResultJson = """
                { "head": {
                            "vars": [ "property" , "propertyLabel" , "dataType", "nullhandling" ]
                          } ,
                          "results": {
                            "bindings": [
                              {\s
                                "property": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#jobLocationType" } ,
                                "propertyLabel": { "type": "literal" , "value": "Job Location Type" } ,
                                "dataType": { "type": "uri" , "value": "http://www.w3.org/2001/XMLSchema#string" }
                              } ,
                              {\s
                                "property": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#requiresSkill" } ,
                                "propertyLabel": { "type": "literal" , "value": "Requires Skill" } ,
                                "dataType": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Skill" }
                              } ,
                              {\s
                                "property": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#postedByCompany" } ,
                                "propertyLabel": { "type": "literal" , "value": "Posted By Company" } ,
                                "dataType": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Company" }
                              } ,
                              {\s
                                "property": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#jobTitle" } ,
                                "propertyLabel": { "type": "literal" , "value": "Job Title" } ,
                                "dataType": { "type": "uri" , "value": "http://www.w3.org/2001/XMLSchema#string" }
                              } ,
                              {\s
                                "property": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#jobLocation" } ,
                                "propertyLabel": { "type": "literal" , "value": "Job Location" } ,
                                "dataType": { "type": "uri" , "value": "http://www.w3.org/2001/XMLSchema#string" }
                              } ,
                              {\s
                                "property": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#experinceLevel" } ,
                                "propertyLabel": { "type": "literal" , "value": "Experience in Level" } ,
                                "dataType": { "type": "uri" , "value": "http://www.w3.org/2001/XMLSchema#string" }
                              } ,
                              {\s
                                "property": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#employmentType" } ,
                                "propertyLabel": { "type": "literal" , "value": "Employment Type" } ,
                                "dataType": { "type": "uri" , "value": "http://www.w3.org/2001/XMLSchema#string" }
                              } ,
                              {\s
                                "property": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#datePosted" } ,
                                "propertyLabel": { "type": "literal" , "value": "Date Posted" } ,
                                "dataType": { "type": "uri" , "value": "http://www.w3.org/2001/XMLSchema#dateTime" }
                              } ,
                              {\s
                                "property": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#experienceInYears" } ,
                                "propertyLabel": { "type": "literal" , "value": "Experience In Years" } ,
                                "dataType": { "type": "uri" , "value": "http://www.w3.org/2001/XMLSchema#int" }
                              } ,
                              {\s
                                "property": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#requiresEducation" } ,
                                "propertyLabel": { "type": "literal" , "value": "Requires Education" } ,
                                "dataType": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Education" }
                              }
                            ]
                          }
                        }
        """;
// Convert the JSON string to a ByteArrayInputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(sparqlResultJson.getBytes());

        // Use ResultSetFactory to create ResultSet from the InputStream
        MOCK_PROPERTIES_RESULT_SET = ResultSetFactory.fromJSON(inputStream);
    }

    static {

        String sparqlResultJson = """
                { "head": {
                            "vars": [ "instance" , "label", "nullhandling" ]
                          } ,
                          "results": {
                            "bindings": [
                              {\s
                                "instance": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#AmazonJavaDeveloper" } ,
                                "label": { "type": "literal" , "value": "Amazon Java Developer" }
                              } ,
                              {\s
                                "instance": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Software_Developer" } ,
                                "label": { "type": "literal" , "value": "Software Developer" }
                              }
                            ]
                          }
                        }
        """;
// Convert the JSON string to a ByteArrayInputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(sparqlResultJson.getBytes());

        // Use ResultSetFactory to create ResultSet from the InputStream
        MOCK_INSTANCES_RESULT_SET = ResultSetFactory.fromJSON(inputStream);
    }

    static {

        String sparqlResultJson = """
                { "head": {
                            "vars": [ "subclass" , "label", "nullhandling" ]
                          } ,
                          "results": {
                            "bindings": [
                              {\s
                                "subclass": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Skill" } ,
                                "label": { "type": "literal" , "value": "Skill" }
                              } ,
                              {\s
                                "subclass": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#TechnicalSkill" } ,
                                "label": { "type": "literal" , "value": "Technical Skill" }
                              } ,
                              {\s
                                "subclass": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Library" } ,
                                "label": { "type": "literal" , "value": "Library" }
                              } ,
                              {\s
                                "subclass": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Framework" } ,
                                "label": { "type": "literal" , "value": "Framework" }
                              } ,
                              {\s
                                "subclass": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#ProgrammingLanguage" } ,
                                "label": { "type": "literal" , "value": "Programming Language" }
                              } ,
                              {\s
                                "subclass": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#SoftSkill" } ,
                                "label": { "type": "literal" , "value": "Soft Skill" }
                              } ,
                              {\s
                                "subclass": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#LanguageSkill" } ,
                                "label": { "type": "literal" , "value": "Language Skill" }
                              }
                            ]
                          }
                        }
        """;
// Convert the JSON string to a ByteArrayInputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(sparqlResultJson.getBytes());

        // Use ResultSetFactory to create ResultSet from the InputStream
        MOCK_SUBCLASSES_RESULT_SET = ResultSetFactory.fromJSON(inputStream);
    }

    static {

        String sparqlResultJson = """
                { "head": {
                            "vars": [ "subclass" , "label" , "description", "nullhandling" ]
                          } ,
                          "results": {
                            "bindings": [
                              {\s
                                "subclass": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Java" } ,
                                "label": { "type": "literal" , "value": "Java" }
                              } ,
                              {\s
                                "subclass": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Python" } ,
                                "label": { "type": "literal" , "value": "Python" }
                              }
                            ]
                          }
                        }
        """;
// Convert the JSON string to a ByteArrayInputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(sparqlResultJson.getBytes());

        // Use ResultSetFactory to create ResultSet from the InputStream
        MOCK_FILTERED_SUBCLASS_INSTANCES_RESULT_SET = ResultSetFactory.fromJSON(inputStream);
    }

    static {

        String sparqlResultJson = """
                { "head": {
                            "vars": [ "entity" , "label" , "class", "nullhandling" ]
                          } ,
                          "results": {
                            "bindings": [
                              {\s
                                "entity": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#Library" } ,
                                "label": { "type": "literal" , "value": "Library" } ,
                                "class": { "type": "uri" , "value": "http://www.w3.org/2002/07/owl#Class" }
                              } ,
                              {\s
                                "entity": { "type": "uri" , "value": "http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#ProgrammingLanguage" } ,
                                "label": { "type": "literal" , "value": "Programming Language" } ,
                                "class": { "type": "uri" , "value": "http://www.w3.org/2002/07/owl#Class" }
                              }
                            ]
                          }
                        }
        """;
// Convert the JSON string to a ByteArrayInputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(sparqlResultJson.getBytes());

        // Use ResultSetFactory to create ResultSet from the InputStream
        MOCK_LABELS_AND_CLASS_RESULT_SET = ResultSetFactory.fromJSON(inputStream);
    }
}
