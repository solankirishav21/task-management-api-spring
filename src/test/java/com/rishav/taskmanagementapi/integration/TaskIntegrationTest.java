package com.rishav.taskmanagementapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rishav.taskmanagementapi.dto.TaskRequestDto;
import com.rishav.taskmanagementapi.model.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void fullFlow_create_get_update_delete() throws Exception {

        TaskRequestDto dto = new TaskRequestDto();
        dto.setTitle("Integration Test");
        dto.setStatus(Status.TODO);
        dto.setPriority("HIGH");

        // CREATE
        mockMvc.perform(post("/api/task")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));

        // GET ALL
        mockMvc.perform(get("/api/task"))
                .andExpect(status().isOk());

        // UPDATE
        dto.setTitle("Updated Task");

        mockMvc.perform(put("/api/task/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        // DELETE
        mockMvc.perform(delete("/api/task/1"))
                .andExpect(status().isOk());
    }
}