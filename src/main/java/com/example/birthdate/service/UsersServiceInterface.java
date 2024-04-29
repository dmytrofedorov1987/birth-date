package com.example.birthdate.service;

import com.example.birthdate.dto.UsersDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface UsersServiceInterface {

    UsersDto createUser(UsersDto dto);

    UsersDto updateUser(Long id, UsersDto dto);

    void deleteUser(Long id);

    List<UsersDto> findUsersByBirthDate(LocalDate from, LocalDate to, Pageable pageable);

    int totalPages(LocalDate from, LocalDate to, int size);
}
