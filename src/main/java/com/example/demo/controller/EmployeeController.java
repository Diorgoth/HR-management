package com.example.demo.controller;

import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.EmployeeResponse;
import com.example.demo.payload.SalaryDto;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public HttpEntity<?> findAll() {
        ApiResponse response = employeeService.allEmployees();
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }





    @PostMapping("/salary")
    public HttpEntity<?> payMonthly(@RequestBody SalaryDto salaryDto) {
        ApiResponse response = employeeService.payMonthly(salaryDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }



    @GetMapping("/byTurniketTask")
    public HttpEntity<?> getAllCompletedTaskByTime(@RequestParam UUID employeeId,
                                                   @RequestParam Timestamp startDateTime,
                                                   @RequestParam Timestamp finishDateTime) {
        EmployeeResponse response = employeeService.byData(employeeId, startDateTime, finishDateTime);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);

    }


    @GetMapping("/salary/byMonthDay")
    public HttpEntity<?> getSalariesByMonth(@RequestParam String year,  @RequestParam Integer monthNumber) {
        ApiResponse response = employeeService.getSalariesByMonth(year, monthNumber);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }


    @GetMapping("/salary/{id}")
    public HttpEntity<?> getSalariesByEmployeeId(@PathVariable UUID id) {
        ApiResponse response = employeeService.getSalariesByUserId(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }

}
