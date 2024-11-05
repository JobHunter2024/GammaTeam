package com.jobhunter24.queryingapi.validators;

import com.jobhunter24.queryingapi.api.model.BaseResponse;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.springframework.stereotype.Component;

@Component
public class SparqlQueryValidator {
    public static BaseResponse isValidSparqlQuery(String sparqlQuery) {
        try {
            Query query = QueryFactory.create(sparqlQuery);
        } catch (Exception e) {
            BaseResponse br = BaseResponse.builder()
                    .message(e.getMessage())
                    .success(false).build();
            return br;
        }
        BaseResponse br = BaseResponse.builder()
                .message("Query successfully validated")
                .success(true).build();
        return br;
    }
}
