package com.example.demo.config;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRunRepository extends JpaRepository<TaskRunOutput, Long> {
}
