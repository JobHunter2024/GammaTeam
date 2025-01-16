package com.jobhunter24.standardqueriesapi.api.controller;

import ch.qos.logback.classic.Logger;
import com.jobhunter24.standardqueriesapi.api.dto.label.EntityLabelsQueryModel;
import com.jobhunter24.standardqueriesapi.api.dto.property.EntityPropertyQueryModel;
import com.jobhunter24.standardqueriesapi.api.dto.subclass.FilteredEntitySubclassQuery;
import com.jobhunter24.standardqueriesapi.api.service.ISparqlService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/entities")
public class QueryController {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(QueryController.class);

    private final ISparqlService sparqlService;
    public QueryController(ISparqlService sparqlService) {
        this.sparqlService = sparqlService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String,Object>>> queryEntities() {
        List<Map<String,Object>> response = sparqlService.getEntities();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("properties")
    public ResponseEntity<List<Map<String,Object>>> queryProperties(@Valid @RequestBody EntityPropertyQueryModel queryModel) {
        List<Map<String,Object>> response = sparqlService.getPropertiesOf(queryModel.entityClass);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("data")
    public ResponseEntity<List<Map<String,Object>>> getAllDataOf(@Valid @RequestBody EntityPropertyQueryModel queryModel) {
        List<Map<String,Object>> response = sparqlService.getAllDataOf(queryModel.entityClass);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("instances")
    public ResponseEntity<List<Map<String,Object>>> getInstancesOf(@Valid @RequestBody EntityPropertyQueryModel queryModel) {
        List<Map<String,Object>> response = sparqlService.getInstancesOf(queryModel.entityClass);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/labels")
    public ResponseEntity<Map<String, Map<String, String>>> getLabelsAndClasses(@Valid @RequestBody EntityLabelsQueryModel queryModel) {
        Map<String, Map<String, String>> response = sparqlService.getLabelsAndClasses(queryModel.entityClass);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/subclasses")
    public ResponseEntity<List<Map<String,Object>>> getSubclassesOf(@Valid @RequestBody EntityPropertyQueryModel queryModel) {
        List<Map<String,Object>> response = sparqlService.getSubclassesOf(queryModel.entityClass);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/subclasses/filter")
    public ResponseEntity<List<Map<String,Object>>> getFilteredSubclassInstancesOf(@Valid @RequestBody FilteredEntitySubclassQuery queryModel) {
        List<Map<String,Object>> response = sparqlService.getFilteredSubclassInstancesOf(queryModel.entityClasses, queryModel.searchTerm);

        return ResponseEntity.ok().body(response);
    }
}
