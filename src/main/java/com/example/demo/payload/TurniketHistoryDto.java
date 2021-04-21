package com.example.demo.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class TurniketHistoryDto {

    private Integer turniketId;

    private Boolean entering; // agar entering true bolasa kirayotgan, false bolsa chiqayotgan hisoblanadi

     private UUID userId;
}
