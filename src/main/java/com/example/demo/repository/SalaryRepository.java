package com.example.demo.repository;

import com.example.demo.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface SalaryRepository extends JpaRepository<Salary,Integer> {

    List<Salary> findAllByEmployeeId(UUID employee_id);
    List<Salary> findAllByWorkStartDate(Date workStartDate);
}
