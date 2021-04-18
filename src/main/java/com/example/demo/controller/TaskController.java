package com.example.demo.controller;

import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.StatusDto;
import com.example.demo.payload.TaskDto;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    TaskService taskService;


    @PostMapping("/addtask")
    public HttpEntity<?> addTask(@RequestBody TaskDto taskDto){

        ApiResponse apiResponce = taskService.addTask(taskDto);

        return  ResponseEntity.status(apiResponce.isSuccess()?201:409).body(apiResponce);
    }

    @PostMapping("/giventask")
    public HttpEntity<?> givenTask(@RequestParam String email){

        ApiResponse apiResponce = taskService.givenTask(email);

        return  ResponseEntity.status(apiResponce.isSuccess()?201:409).body(apiResponce);
    }

    @PostMapping("/taskstatuschange/{id}")
    public HttpEntity<?> taskStatusChange(@PathVariable Integer id, @RequestBody StatusDto statusDto){

        ApiResponse taskstatuschange = taskService.taskstatuschange(id, statusDto.getStatus());

        return  ResponseEntity.status(taskstatuschange.isSuccess()?201:409).body(taskstatuschange);

    }

}
