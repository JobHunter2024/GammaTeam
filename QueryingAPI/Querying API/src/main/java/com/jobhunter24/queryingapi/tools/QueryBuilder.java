package com.jobhunter24.queryingapi.tools;

import com.jobhunter24.queryingapi.api.model.Query;
import org.springframework.stereotype.Component;

@Component
public class QueryBuilder {
    public String buildQuery(Query query) {
        return query.query;
    }
}
