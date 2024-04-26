package com.example.birthdate.controller;

import com.example.birthdate.dto.UsersDto;
import com.example.birthdate.exception.BirthDateRuntimeException;
import com.example.birthdate.service.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exists.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is too young.");
        }
    }

    private int getUserAge(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAllFields(@PathVariable(value = "id") Long id,
                                             @RequestBody @Validated UsersDto dto) {
        try {
            UsersDto updateUser = usersService.updateUser(id, dto);
            return ResponseEntity.ok(updateUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Json failed.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") Long id,
                                        @RequestBody @Validated UsersDto dto) {
        try {
            UsersDto updateUser = usersService.updateUser(id, dto);
            return ResponseEntity.ok(updateUser);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }


}
