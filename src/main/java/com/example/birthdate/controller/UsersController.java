package com.example.birthdate.controller;

import com.example.birthdate.dto.UsersDto;
import com.example.birthdate.exception.BirthDateRuntimeException;
import com.example.birthdate.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {
    @Value("${value.allowedAge}")
    private int allowedAge;
    private final UsersService usersService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Validated UsersDto dto) {
        if (getUserAge(dto.getBirthDate()) >= allowedAge) {
            try {
                UsersDto userDto = usersService.createUser(dto);
                return ResponseEntity.ok(userDto);
            } catch (BirthDateRuntimeException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exists.", ex);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is too young.");
        }
    }

    private int getUserAge(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears();
    }

}
