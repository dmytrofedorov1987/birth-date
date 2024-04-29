package com.example.birthdate.controller;

import com.example.birthdate.dto.UsersDto;
import com.example.birthdate.exception.BirthDateRuntimeException;
import com.example.birthdate.service.UsersService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {
    @Value("${value.allowedAge}")
    private int allowedAge;
    private final static Integer CURRENT_PAGE_DEFAULT = 0;
    private final static Integer PAGE_SIZE_DEFAULT = 5;
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
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id) {
        try {
            usersService.deleteUser(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByBirthDate(@RequestParam(name = "date_from") LocalDate from,
                                               @RequestParam(name = "date_to") LocalDate to,
                                               @RequestParam(name = "page_number") Optional<Integer> page) {
        int currentPage = page.orElse(CURRENT_PAGE_DEFAULT);
        if (currentPage < usersService.totalPages(from, to, PAGE_SIZE_DEFAULT)) {
            return ResponseEntity.ok(usersService.findUsersByBirthDate(from, to, PageRequest.of(currentPage, PAGE_SIZE_DEFAULT)));
        } else {
            throw new BirthDateRuntimeException("Page Number is not correct.");
        }
    }
}
