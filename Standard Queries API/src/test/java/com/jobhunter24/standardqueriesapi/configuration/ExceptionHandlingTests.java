package com.jobhunter24.standardqueriesapi.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhunter24.standardqueriesapi.api.controller.QueryController;
import com.jobhunter24.standardqueriesapi.api.service.SparqlService;
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

import static com.jobhunter24.standardqueriesapi.utils.Constants.FILTERED_ENTITY_SUBCLASS_QUERY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(QueryController.class)
@AutoConfigureMockMvc
public class ExceptionHandlingTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    SparqlService sparqlService;

    @Test
    void givenValidRequest_whenUnexpectedExceptionIsThrown_thenHandleExceptionGracefullyReturning500InternalServerError() throws Exception {
        doThrow(RuntimeException.class).when(sparqlService).getFilteredSubclassInstancesOf(any(List.class),any(String.class));
        String requestBody = objectMapper.writeValueAsString(FILTERED_ENTITY_SUBCLASS_QUERY);

        mockMvc.perform(post("/api/v1/entities/subclasses/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError());
    }
}
