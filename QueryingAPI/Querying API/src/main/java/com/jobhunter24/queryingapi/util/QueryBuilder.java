package com.jobhunter24.queryingapi.util;

import com.jobhunter24.queryingapi.api.model.DataObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QueryBuilder {
    // Map to track occurrences of properties
    private Map<String, Integer> entityCounts = new HashMap<>();
    // Map to track entity labels for reuse
    private Map<String, String> entityLabels = new HashMap<>();

    public String generateSparqlQuery(DataObject dataObject) {
        StringBuilder sparql = new StringBuilder();

        // Base subject
        String subject = dataObject.getSubject();
        List<String> instances = dataObject.getInstances();
        Map<String, Object> dataProperties = dataObject.getDataProperties();
        Map<String, Object> objectProperties = dataObject.getObjectProperties();

        // Add required PREFIX declarations
        sparql.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ");
        sparql.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ");
        sparql.append("PREFIX ont: <http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#> ");
        sparql.append("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "); // Add this line
        sparql.append("SELECT ?instance ?label ?description WHERE { ");

        // Add rdf:type and rdfs:subClassOf* relationship
        sparql.append("  ?instance rdf:type ?subclass . ");
        sparql.append("  ?subclass rdfs:subClassOf* <").append(subject).append("> . ");

        // Add filter for specific instances (if provided) or all instances
        if (instances != null && !instances.isEmpty()) {
            sparql.append("  FILTER(?instance IN (");
            for (int i = 0; i < instances.size(); i++) {
                sparql.append("<").append(instances.get(i)).append(">");
                if (i < instances.size() - 1) sparql.append(", ");
            }
            sparql.append(")) . ");
        }

        // Add filters for data properties
        if (dataProperties != null) {
            for (Map.Entry<String, Object> entry : dataProperties.entrySet()) {
                String property = entry.getKey();
                Object value = entry.getValue();

                if (property.equals("http://www.semanticweb.org/ana/ontologies/2024/10/JobHunterOntology#datePosted")) {
                    if (value instanceof Map) {
                        Map<String, String> dateRange = (Map<String, String>) value;
                        String startDate = dateRange.get("startDate");
                        String endDate = dateRange.get("endDate");

                        sparql.append("  OPTIONAL { ?instance <").append(property).append("> ?datePosted . } ");
                        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
                            startDate = ensureDateFormat(startDate);
                            endDate = ensureDateFormat(endDate);

                            sparql.append("  FILTER(?datePosted >= \"").append(startDate).append("\"^^xsd:dateTime ");
                            sparql.append("  && ?datePosted <= \"").append(endDate).append("\"^^xsd:dateTime) . ");
                        }
                    }
                } else if (value instanceof String) {
                    String stringValue = (String) value;
                    sparql.append("  OPTIONAL { ?instance <").append(property).append("> ?value . } ");
                    if (stringValue != null && !stringValue.isEmpty()) {
                        sparql.append("  FILTER(CONTAINS(LCASE(?value), LCASE(\"").append(stringValue).append("\"))) . ");
                    }
                }
            }
        }

        // Add object properties recursively
        processObjectProperties(sparql, "?instance", objectProperties);

        // Add OPTIONAL clauses for label and description
        sparql.append("  OPTIONAL { ?instance rdfs:label ?label . } ");
        sparql.append("  OPTIONAL { ?instance rdfs:comment ?description . } ");

        sparql.append("} ");
        return sparql.toString();
    }

    private void processObjectProperties(StringBuilder sparql, String parentVariable,
                                         Map<String, Object> objectProperties) {
        if (objectProperties == null || objectProperties.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : objectProperties.entrySet()) {
            String property = entry.getKey();
            Map<String, Object> nestedObject = (Map<String, Object>) entry.getValue();

            // Define related instance variable with meaningful names (e.g., ?skill1, ?company1)
            String entityVariable = getEntityVariableName(property); // Ensure unique name
            String nestedSubject = (String) nestedObject.get("subject");
            List<String> nestedInstances = (List<String>) nestedObject.get("instances");
            Map<String, String> nestedDataProperties = (Map<String, String>) nestedObject.get("dataProperties");
            Map<String, Object> nestedObjectProperties = (Map<String, Object>) nestedObject.get("objectProperties");

            // Assign distinct variable names for subclass types based on entity
            String subclassVariable = getSubclassVariableName(property);

            sparql.append("  ").append(parentVariable).append(" <").append(property).append("> ")
                    .append(entityVariable).append(" . ");
            sparql.append("  ").append(entityVariable).append(" rdf:type ").append(subclassVariable).append(" . ");
            sparql.append("  ").append(subclassVariable).append(" rdfs:subClassOf* <").append(nestedSubject).append("> . ");

            // Add nested instances
            if (nestedInstances != null && !nestedInstances.isEmpty()) {
                sparql.append("  FILTER(").append(entityVariable).append(" IN (");
                for (int i = 0; i < nestedInstances.size(); i++) {
                    sparql.append("<").append(nestedInstances.get(i)).append(">");
                    if (i < nestedInstances.size() - 1) sparql.append(", ");
                }
                sparql.append(")) . ");
            }

            // Add nested data properties
            if (nestedDataProperties != null) {
                for (Map.Entry<String, String> nestedEntry : nestedDataProperties.entrySet()) {
                    String nestedProperty = nestedEntry.getKey();
                    String nestedValue = nestedEntry.getValue();
                    sparql.append("  OPTIONAL { ").append(entityVariable).append(" <").append(nestedProperty).append("> ?nestedValue . } ");
                    if (nestedValue != null && !nestedValue.isEmpty()) {
                        sparql.append("  FILTER(CONTAINS(LCASE(?nestedValue), LCASE(\"").append(nestedValue).append("\"))) . ");
                    }
                }
            }

            // Recursive call for further nested object properties
            processObjectProperties(sparql, entityVariable, nestedObjectProperties);
        }
    }

    // Helper function to map property URIs to meaningful entity variable names with unique indices
    private String getEntityVariableName(String property) {
        // If the property URI has already been encountered, return the stored label
        if (entityLabels.containsKey(property)) {
            return entityLabels.get(property);
        }

        // Increment the count for this property
        int count = entityCounts.getOrDefault(property, 0) + 1;
        entityCounts.put(property, count);

        // Generate a new entity variable name based on the count
        String entityVariable = "?"+ Math.abs(property.hashCode()) + "_" + count;  // Make the variable name distinct
        entityLabels.put(property, entityVariable);  // Store the generated variable for reuse
        return entityVariable;
    }

    // Helper function to generate distinct subclass variable names based on the property URI
    private String getSubclassVariableName(String property) {
        // If the subclass for this property URI has already been generated, return the stored label
        if (entityLabels.containsKey(property + "_subclass")) {
            return entityLabels.get(property + "_subclass");
        }

        // Generate a new subclass variable name
        String subclassVariable = "?" + Math.abs(property.hashCode()) + "_" + (entityCounts.getOrDefault(property, 0) + 1);
        entityLabels.put(property + "_subclass", subclassVariable);  // Store the generated subclass for reuse
        return subclassVariable;
    }

    public String buildQuery(DataObject dataObject) {
        return generateSparqlQuery(dataObject);
    }

    private Map<String, String> parseDateRange(String rawValue) {
        // Implement JSON parsing logic, e.g., using Jackson or Gson
        return new HashMap<>(); // Placeholder: return parsed startDate and endDate
    }

    // Helper function to ensure the date format includes time (with seconds)
    private String ensureDateFormat(String date) {
        if (date != null && !date.isEmpty()) {
            // If the date does not contain time, append "T00:00:00"
            if (date.length() == 10) { // Only YYYY-MM-DD, no time
                return date + "T00:00:00";
            }
            // If the date has time but no seconds, add ":00" to complete it
            if (date.length() == 16) { // YYYY-MM-DDTHH:mm (missing seconds)
                return date + ":00";  // Add seconds as 00
            }
            // Return the date as is if it's already in the correct format
            return date;
        }
        return "";
    }
}