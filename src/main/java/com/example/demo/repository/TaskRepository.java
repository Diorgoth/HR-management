package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskStatus.TaskStatus;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface TaskRepository extends JpaRepository<Task,Integer> {

boolean existsByIdAndResId_Email(Integer id, String resId_email);

   List<Task> findByResId_Email(String resId_email);
   List<Task> findAllByStatusAndResponsibleId(TaskStatus status, UUID responsible_id);
}
