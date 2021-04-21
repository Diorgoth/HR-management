package com.example.demo.controller;


import com.example.demo.entity.Turniket;
import com.example.demo.payload.ApiResponse;
import com.example.demo.service.TurniketService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/turniket")
public class TurniketController {
    @Autowired
    TurniketService turniketService;


    @PostMapping
    public HttpEntity<?> addTurniket(@RequestBody Turniket turniket){

        ApiResponse apiResponse = turniketService.addTurniket(turniket);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);

    }

    @PutMapping("/{id}")
    public HttpEntity<?> editTurniket(@PathVariable Integer id, @RequestBody Turniket turniket){

        ApiResponse apiResponse = turniketService.editTurniket(id, turniket);

        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);

    }

    @GetMapping
    public HttpEntity<?> getAll(){

        ApiResponse apiResponse = turniketService.getAll();

        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);

    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(Integer id){

        ApiResponse delete = turniketService.delete(id);
        return ResponseEntity.status(delete.isSuccess() ? 201 : 409).body(delete);

    }



}
