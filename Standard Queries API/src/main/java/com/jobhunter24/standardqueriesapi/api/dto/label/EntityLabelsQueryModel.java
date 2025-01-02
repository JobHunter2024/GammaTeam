package com.jobhunter24.standardqueriesapi.api.dto.label;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class EntityLabelsQueryModel {
    public List<String> entityClass;
}
