package com.jobhunter24.queryingapi.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class Query {
    public String query;
}
