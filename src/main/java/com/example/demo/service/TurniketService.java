package com.example.demo.service;


import com.example.demo.entity.Role;
import com.example.demo.entity.Turniket;
import com.example.demo.entity.User;
import com.example.demo.payload.ApiResponse;
import com.example.demo.repository.TurniketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
public class TurniketService {

    @Autowired
    TurniketRepository turniketRepository;


    public ApiResponse addTurniket(Turniket turniket){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Set<Role> roles = user.getRoles();

        for (Role role:roles) {

            if (!role.getLevel().equals(1)){
                return new ApiResponse("You are not eligible for this transaction",false);
            }

        }

        turniketRepository.save(turniket);

        return new ApiResponse("Tourniquet successfully added ",true);

    }

    public ApiResponse editTurniket(Integer id,Turniket turniket){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Set<Role> roles = user.getRoles();

        for (Role role:roles) {

            if (!role.getLevel().equals(1)){
                return new ApiResponse("You are not eligible for this transaction",false);
            }

        }

        Optional<Turniket> optionalTurniket = turniketRepository.findById(id);

        if (!optionalTurniket.isPresent()){
            return new ApiResponse("Such Tourniquet not found",false);
        }

        Turniket turniket1 = optionalTurniket.get();

        turniket1.setLocation(turniket.getLocation());
        turniket1.setStatus(turniket.isStatus());

        turniketRepository.save(turniket1);

        return new ApiResponse("Tourniquet successfully edited",true);


    }



    public ApiResponse getAll(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Set<Role> roles = user.getRoles();

        for (Role role:roles) {

            if (!role.getLevel().equals(1)){
                return new ApiResponse("You are not eligible for this transaction",false);
            }

        }

        return new ApiResponse("Tourniquets",true,turniketRepository.findAll());

    }

    public ApiResponse delete(Integer id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Set<Role> roles = user.getRoles();

        for (Role role:roles) {

            if (!role.getLevel().equals(1)){
                return new ApiResponse("You are not eligible for this transaction",false);
            }

        }

        Optional<Turniket> optionalTurniket = turniketRepository.findById(id);

        if (!optionalTurniket.isPresent()){

            return new ApiResponse("There is no such Tourniquet with this id",false);

        }

        turniketRepository.deleteById(id);
        return new ApiResponse("Tourniquet deleted",true);

    }



}
