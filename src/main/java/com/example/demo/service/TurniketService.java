package com.example.demo.service;

import com.example.demo.entity.Turniket;
import com.example.demo.entity.User;
import com.example.demo.payload.ApiResponse;
import com.example.demo.repository.TurniketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TurniketService {

    @Autowired
    TurniketRepository turniketRepository;


    public ApiResponse enterWork(){

        Turniket turniket = new Turniket();

        turniket.setStatus(true);
        turniket.setEnterDateTime(LocalDateTime.now());

        turniketRepository.save(turniket);

        return new ApiResponse("You successfully entered",true);
    }

    public ApiResponse exitWork(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {

            User user = (User) authentication.getPrincipal();



            Optional<Turniket> optionalTurniket = turniketRepository.findByCreatedByAndStatus(user.getId(), true);
            if (!optionalTurniket.isPresent())
                return new ApiResponse("Such turniket id not found!", false);

            optionalTurniket.get().setStatus(false);
            optionalTurniket.get().setExitDateTime(LocalDateTime.now());

            turniketRepository.save(optionalTurniket.get());

            return new ApiResponse("Success! You exited!", true);
        }

        return new ApiResponse("Problem in authentication ",false);
    }


}
