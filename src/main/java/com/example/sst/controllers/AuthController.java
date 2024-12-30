package com.example.sst.controllers;

import com.example.sst.dtos.PassengerDto;
import com.example.sst.dtos.PassengerSignupRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signupPassenger(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        return  null;
    }

}
