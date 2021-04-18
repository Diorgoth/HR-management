package com.example.demo.payload;

import com.example.demo.entity.TaskStatus.TaskStatus;

import lombok.Data;


import java.sql.Date;
import java.util.UUID;

@Data
public class TaskDto {


    private String name;



    private String description;


    private Date deadline;


    private UUID resId;//Responsible user for this task



    private TaskStatus status;

}
