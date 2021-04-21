package com.example.demo.service;


import com.example.demo.entity.ActionType.TurniketType;
import com.example.demo.entity.Turniket;
import com.example.demo.entity.TurniketHistory;
import com.example.demo.entity.User;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.TurniketHistoryDto;
import com.example.demo.repository.TurniketHistoryRepository;
import com.example.demo.repository.TurniketRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TurniketHistoryService {

    @Autowired
    TurniketHistoryRepository turniketHistoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TurniketRepository turniketRepository;

    public ApiResponse turniketHistory(TurniketHistoryDto turniketHistoryDto){

        Optional<User> optionalUser = userRepository.findById(turniketHistoryDto.getUserId());
        if (!optionalUser.isPresent()){
            return new ApiResponse("User not found",false);
        }

        Optional<Turniket> optionalTurniket = turniketRepository.findById(turniketHistoryDto.getTurniketId());

        if (!optionalTurniket.isPresent()){
            return new ApiResponse("Turniket not found",false);
        }


        if (!optionalTurniket.get().isStatus()){
            return new ApiResponse("Turniket is switched off by Director",false);
        }

        TurniketHistory turniketHistory = new TurniketHistory();
        if(turniketHistoryDto.getEntering()){

            turniketHistory.setTurniket(optionalTurniket.get());
            turniketHistory.setActionType(TurniketType.ENTER);
            turniketHistory.setUser(optionalUser.get());

            turniketHistoryRepository.save(turniketHistory);
            return new ApiResponse("History added",true);

        }else {

            turniketHistory.setTurniket(optionalTurniket.get());
            turniketHistory.setActionType(TurniketType.EXIT);
            turniketHistory.setUser(optionalUser.get());

            turniketHistoryRepository.save(turniketHistory);
            return new ApiResponse("History added",true);

        }


    }




}
