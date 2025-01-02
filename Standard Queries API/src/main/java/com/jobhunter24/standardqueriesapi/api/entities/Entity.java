package com.jobhunter24.standardqueriesapi.api.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Entity {
    private String description;
    private String label;
    private String classUri;
}
