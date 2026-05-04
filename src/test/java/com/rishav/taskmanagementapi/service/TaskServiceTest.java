package com.rishav.taskmanagementapi.service;

import com.rishav.taskmanagementapi.dto.TaskRequestDto;
import com.rishav.taskmanagementapi.exception.ResourceNotFoundException;
import com.rishav.taskmanagementapi.model.Status;
import com.rishav.taskmanagementapi.model.Task;
import com.rishav.taskmanagementapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private TaskRequestDto createDto() {
        TaskRequestDto dto = new TaskRequestDto();
        dto.setTitle("Test Task");
        dto.setDescription("Desc");
        dto.setStatus(Status.TODO);
        dto.setPriority("HIGH");
        return dto;
    }

    @Test
    void createTask_success() {
        TaskRequestDto dto = createDto();

        Task savedTask = Task.builder()
                .id(1L)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .build();

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        var result = taskService.createTask(dto);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
    }

    @Test
    void getTaskById_success() {
        Task task = Task.builder().id(1L).title("Test").build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        var result = taskService.getTaskById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getTaskById_notFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.getTaskById(1L));
    }

    @Test
    void updateTask_success() {
        Task existing = Task.builder().id(1L).title("Old").build();
        TaskRequestDto dto = createDto();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenReturn(existing);

        var result = taskService.updateTask(1L, dto);

        assertEquals("Test Task", result.getTitle());
    }

    @Test
    void deleteTask_success() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.deleteTask(1L);

        verify(taskRepository).deleteById(1L);
    }

    @Test
    void deleteTask_notFound() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.deleteTask(1L));
    }
}