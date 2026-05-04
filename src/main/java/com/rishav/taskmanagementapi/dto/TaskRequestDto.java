package com.rishav.taskmanagementapi.dto;

import com.rishav.taskmanagementapi.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskRequestDto {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;

    @NotNull(message = "Status is required")
    private Status status;

    @NotBlank(message = "Priority is required")
    private String priority;
}
