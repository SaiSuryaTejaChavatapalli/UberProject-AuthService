package com.example.sst.services;

import com.example.sst.dtos.PassengerDto;
import com.example.sst.dtos.PassengerSignupRequestDto;
import com.example.sst.models.Passenger;
import com.example.sst.repositories.PassengerRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PassengerRepository passengerRepository;

    public AuthService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }


    // We can use mapstruct to these kind of conversions

    public PassengerDto signupPassenger(PassengerSignupRequestDto passengerSignupRequestDto){
        Passenger passenger=Passenger.builder()
                .email(passengerSignupRequestDto.getEmail())
                .name(passengerSignupRequestDto.getName())
                .password(passengerSignupRequestDto.getPassword()) // TODO: Encrypt the password
                .phoneNumber(passengerSignupRequestDto.getPhoneNumber())
                .build();

        Passenger newPassenger=passengerRepository.save(passenger);
        return PassengerDto.from(newPassenger);
    }
}
