package com.jobhunter24.queryingapi.api.model;

import lombok.Builder;

import java.util.List;

@Builder
public class Query {
    public List<Relation> relations;
    public String query;
}
