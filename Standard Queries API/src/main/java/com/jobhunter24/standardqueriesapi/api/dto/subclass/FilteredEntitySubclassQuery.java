package com.jobhunter24.standardqueriesapi.api.dto.subclass;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class FilteredEntitySubclassQuery {
    public List<String> entityClasses;
    public String searchTerm;
}
