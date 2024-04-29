package com.example.birthdate.service;

import com.example.birthdate.dto.UsersDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;

public interface UsersServiceInterface {

    UsersDto createUser(UsersDto dto);

    UsersDto updateUser(Long id, UsersDto dto) throws JsonProcessingException, InvocationTargetException, IllegalAccessException;

    void deleteUser(Long id);

    List<UsersDto> findUsersByBirthDate(LocalDate from, LocalDate to, Pageable pageable);

    int totalPages(LocalDate from, LocalDate to, int size);
}
