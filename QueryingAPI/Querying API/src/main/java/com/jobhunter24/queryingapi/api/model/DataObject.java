package com.jobhunter24.queryingapi.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataObject {
    public String subject;
    public List<String> instances;
    public Map<String, String> dataProperties;
    public Map<String, Object> objectProperties;
}
