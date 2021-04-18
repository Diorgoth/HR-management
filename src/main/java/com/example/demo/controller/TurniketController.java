package com.example.demo.controller;

import com.example.demo.payload.ApiResponse;
import com.example.demo.service.TurniketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/turniket")
public class TurniketController {
    @Autowired
    TurniketService turniketService;

    @PostMapping
    public HttpEntity<?> enterToWork() {
        ApiResponse response = turniketService.enterWork();
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }

    @PutMapping
    public HttpEntity<?> exitFromWork(){
        ApiResponse response = turniketService.exitWork();
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }


}
