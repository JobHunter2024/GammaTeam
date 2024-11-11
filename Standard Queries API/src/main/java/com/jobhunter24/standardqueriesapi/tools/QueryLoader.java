package com.jobhunter24.standardqueriesapi.tools;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class QueryLoader {

    private Map<String, String> queries;

    @PostConstruct
    public void init() throws IOException {
        // Load JSON file from resources
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/queries.json");
        queries = mapper.readValue(inputStream, new TypeReference<Map<String, String>>() {});
    }

    public String getQuery(String key) {
        return queries.get(key);
    }
}