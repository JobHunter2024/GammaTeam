package com.jobhunter24.queryingapi.api.model;

import lombok.Builder;

import java.util.List;
import java.util.Map;


@Builder
public class Response {
    public List<Map<String, Object>> resultList;
    public boolean success;
    public String message;
}
