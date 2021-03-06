package com.example.demo.payload;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;
@Data
public class SalaryDto {

    private UUID employeeId;
    private double salaryAmount;
    private Date workStartDate;
    private Date workEndDate;

}
