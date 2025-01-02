package com.example.sst.controllers;

import com.example.sst.dtos.PassengerDto;
import com.example.sst.dtos.PassengerSignupRequestDto;
import com.example.sst.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public  AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signupPassenger(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        PassengerDto response=authService.signupPassenger(passengerSignupRequestDto);
        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/signin/passenger")
    public ResponseEntity<?> signinPassenger( ){
        return  new ResponseEntity<>(10, HttpStatus.OK);
    }

}
