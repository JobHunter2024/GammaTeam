package com.jobhunter24.queryingapi.validators;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;

public class SparqlQueryValidator {
    public static boolean isValidSparqlQuery(String sparqlQuery) {
        try {
            Query query = QueryFactory.create(sparqlQuery);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
