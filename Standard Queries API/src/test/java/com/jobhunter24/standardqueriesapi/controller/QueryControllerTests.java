package com.jobhunter24.standardqueriesapi.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhunter24.standardqueriesapi.api.controller.QueryController;
import com.jobhunter24.standardqueriesapi.api.service.SparqlService;
import com.jobhunter24.standardqueriesapi.utils.Responses;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.jobhunter24.standardqueriesapi.utils.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(QueryController.class)
@AutoConfigureMockMvc
class QueryControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    SparqlService sparqlService;

    @Test
    void givenValidRequest_whenQueryingEntities_thenReturnListWith200Ok() throws Exception {
        when(sparqlService.getEntities()).thenReturn(Responses.getEntitiesList());

        mockMvc.perform(get("/api/v1/entities"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void givenInvalidRequest_whenQueryingEntities_thenReturn400BadRequest() throws Exception {
        doThrow(IllegalArgumentException.class).when(sparqlService).getEntities();

        mockMvc.perform(get("/api/v1/entities"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenValidRequest_whenQueryingProperties_thenReturnListWith200Ok() throws Exception {
        when(sparqlService.getPropertiesOf(any(String.class))).thenReturn(Responses.getPropertiesList());

        String requestBody = objectMapper.writeValueAsString(ENTITY_PROPERTY_QUERY_MODEL);

        mockMvc.perform(post("/api/v1/entities/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void givenInvalidRequest_whenQueryingProperties_thenReturn400BadRequest() throws Exception {
        doThrow(IllegalArgumentException.class).when(sparqlService).getPropertiesOf(any(String.class));

        String requestBody = objectMapper.writeValueAsString(ENTITY_PROPERTY_QUERY_MODEL); //Invalid EntityClass name case

        mockMvc.perform(post("/api/v1/entities/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenValidRequest_whenQueryingInstances_thenReturnListWith200Ok() throws Exception {
        when(sparqlService.getPropertiesOf(any(String.class))).thenReturn(Responses.getPropertiesList());

        String requestBody = objectMapper.writeValueAsString(ENTITY_PROPERTY_QUERY_MODEL);

        mockMvc.perform(post("/api/v1/entities/instances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void givenInvalidRequest_whenQueryingInstances_thenReturn400BadRequest() throws Exception {
        doThrow(IllegalArgumentException.class).when(sparqlService).getInstancesOf(any(String.class));

        String requestBody = objectMapper.writeValueAsString(ENTITY_PROPERTY_QUERY_MODEL); //Invalid EntityClass name case

        mockMvc.perform(post("/api/v1/entities/instances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenValidRequest_whenQueryingLabels_thenListOfMapsWith200Ok() throws Exception {
        when(sparqlService.getLabelsAndClasses(any(List.class))).thenReturn(Responses.getLabels());

        String requestBody = objectMapper.writeValueAsString(ENTITY_LABELS_QUERY_MODEL);

        mockMvc.perform(post("/api/v1/entities/labels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void givenValidRequest_whenQueryingSubclasses_thenListWith200Ok() throws Exception {
        when(sparqlService.getSubclassesOf(any(String.class))).thenReturn(Responses.getSubclassesList());

        String requestBody = objectMapper.writeValueAsString(ENTITY_PROPERTY_QUERY_MODEL);

        mockMvc.perform(post("/api/v1/entities/subclasses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void givenValidRequest_whenQueryingFilteredSubclasses_thenListWith200Ok() throws Exception {
        when(sparqlService.getFilteredSubclassInstancesOf(any(List.class),any(String.class))).thenReturn(Responses.getSubclassesList());

        String requestBody = objectMapper.writeValueAsString(FILTERED_ENTITY_SUBCLASS_QUERY);

        mockMvc.perform(post("/api/v1/entities/subclasses/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
