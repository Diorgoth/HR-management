package com.example.demo.controller;

import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.TurniketHistoryDto;
import com.example.demo.service.TurniketHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/turnikethistory")
public class TurniketHistoryController {

    @Autowired
    TurniketHistoryService turniketHistoryService;

    @PostMapping
    public HttpEntity<?> addHistory(@RequestBody TurniketHistoryDto turniketHistoryDto){

        ApiResponse apiResponse = turniketHistoryService.turniketHistory(turniketHistoryDto);

        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);

    }

}
