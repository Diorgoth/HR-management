package com.example.demo.entity;

import com.example.demo.entity.TaskStatus.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date deadline;

    @ManyToOne
    private User resId;//Responsible user for this task

    @ManyToOne
    private User createdBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

}
