package com.rishav.taskmanagementapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rishav.taskmanagementapi.dto.TaskRequestDto;
import com.rishav.taskmanagementapi.dto.TaskResponseDto;
import com.rishav.taskmanagementapi.model.Status;
import com.rishav.taskmanagementapi.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTask_success() throws Exception {
        TaskRequestDto dto = new TaskRequestDto();
        dto.setTitle("Test");
        dto.setStatus(Status.TODO);
        dto.setPriority("HIGH");

        TaskResponseDto response = TaskResponseDto.builder()
                .id(1L)
                .title("Test")
                .build();

        when(taskService.createTask(any())).thenReturn(response);

        mockMvc.perform(post("/api/task")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTasks_success() throws Exception {
        TaskResponseDto dto = TaskResponseDto.builder()
                .id(1L)
                .title("Test")
                .build();

        when(taskService.getAllTasks(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        mockMvc.perform(get("/api/task"))
                .andExpect(status().isOk());
    }

    @Test
    void getTaskById_success() throws Exception {
        TaskResponseDto dto = TaskResponseDto.builder().id(1L).build();

        when(taskService.getTaskById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/task/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTask_success() throws Exception {
        mockMvc.perform(delete("/api/task/1"))
                .andExpect(status().isOk());
    }
}