package com.rishav.taskmanagementapi.service;


import com.rishav.taskmanagementapi.dto.TaskRequestDto;
import com.rishav.taskmanagementapi.dto.TaskResponseDto;
import com.rishav.taskmanagementapi.exception.ResourceNotFoundException;
import com.rishav.taskmanagementapi.model.Status;
import com.rishav.taskmanagementapi.model.Task;
import com.rishav.taskmanagementapi.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    private TaskResponseDto mapToResponse(Task task){
        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .build();
    }

    private Task mapToEntity(TaskRequestDto dto){
        return Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .build();
    }


    public TaskResponseDto createTask(TaskRequestDto dto){
        Task task = mapToEntity(dto);
        Task saved = taskRepository.save(task);
        return mapToResponse(saved);
    }

    public Page<TaskResponseDto> getAllTasks(Pageable pageable) {
        Page<Task> page = taskRepository.findAll(pageable);

        return page.map(this::mapToResponse);
    }

    public TaskResponseDto getTaskById(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        return mapToResponse(task);
    }

    public TaskResponseDto updateTask(Long id, TaskRequestDto dto){
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        existingTask.setTitle(dto.getTitle());
        existingTask.setDescription(dto.getDescription());
        existingTask.setStatus(dto.getStatus());
        existingTask.setPriority(dto.getPriority());

        return mapToResponse(taskRepository.save(existingTask));
    }

    public void deleteTask(Long id){
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

}
