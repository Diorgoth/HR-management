package com.example.demo.controller;


import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.RegisterDto;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody RegisterDto registerDto){

        ApiResponse apiResponce = authService.registerUser(registerDto);

        return ResponseEntity.status(apiResponce.isSuccess()?201:409).body(apiResponce);

    }

    @PostMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email,@RequestBody LoginDto loginDto){

        ApiResponse apiResponce = authService.verifyEmail(emailCode,email,loginDto);

        return ResponseEntity.status(apiResponce.isSuccess()?200:409).body(apiResponce);

    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){

        ApiResponse apiResponce = authService.login(loginDto);

        return ResponseEntity.status(apiResponce.isSuccess()?200:401).body(apiResponce);

    }

}
