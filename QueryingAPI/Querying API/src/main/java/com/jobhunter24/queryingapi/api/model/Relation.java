package com.jobhunter24.queryingapi.api.model;

import lombok.Builder;

@Builder
public class Relation {
    public String subject;
    public String predicate;
    public String object;
}
