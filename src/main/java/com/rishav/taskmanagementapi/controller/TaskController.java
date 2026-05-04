package com.rishav.taskmanagementapi.controller;


import com.rishav.taskmanagementapi.dto.TaskRequestDto;
import com.rishav.taskmanagementapi.dto.TaskResponseDto;
import com.rishav.taskmanagementapi.model.Task;
import com.rishav.taskmanagementapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String homeController(){
        return "Home Page";
    }

    @PostMapping("/task")
    public TaskResponseDto createTask(@Valid @RequestBody TaskRequestDto dto){
        return taskService.createTask(dto);
    }

    @GetMapping("/task")
    public List<TaskResponseDto> getAllTask(){
        return taskService.getAllTasks();
    }

    @GetMapping("/task/{id}")
    public TaskResponseDto getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @PutMapping("/task/{id}")
    public TaskResponseDto updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDto dto){
        return taskService.updateTask(id, dto);
    }

    @DeleteMapping("/task/{id}")
    public String deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return "Task deleted successfully!";
    }
}
