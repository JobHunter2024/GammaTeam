package com.jobhunter24.standardqueriesapi.api.controller;

import ch.qos.logback.classic.Logger;
import com.jobhunter24.standardqueriesapi.api.dto.properties.EntityPropertyQueryModel;
import com.jobhunter24.standardqueriesapi.api.service.ISparqlService;
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
    public ResponseEntity<List<Map<String,Object>>> query() {
        try {
            List<Map<String,Object>> response = sparqlService.getEntities();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/properties")
    public ResponseEntity<List<Map<String,Object>>> queryProperties(@RequestBody EntityPropertyQueryModel queryModel) {
        try {
            logger.info("EntityClass " + queryModel.entityClass);
            List<Map<String,Object>> response = sparqlService.getPropertiesOf(queryModel.entityClass);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
