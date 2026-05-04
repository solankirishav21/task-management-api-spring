package com.rishav.taskmanagementapi.repository;

import com.rishav.taskmanagementapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
