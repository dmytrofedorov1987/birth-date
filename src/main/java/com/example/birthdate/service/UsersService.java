package com.example.birthdate.service;

import com.example.birthdate.dto.UsersDto;
import com.example.birthdate.exception.BirthDateRuntimeException;
import com.example.birthdate.mapper.UsersMapper;
import com.example.birthdate.model.Users;
import com.example.birthdate.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService implements UsersServiceInterface {

    private final UsersRepository userRepository;
    private final UsersMapper usersMapper;

    @Transactional
    @Override
    public UsersDto createUser(UsersDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BirthDateRuntimeException("User with email " + dto.getEmail() + " exists.");
        }
        Users user = usersMapper.toEntity(dto);
        userRepository.save(user);
        return usersMapper.toDto(user);
    }

    @Transactional
    @Override
    public UsersDto updateUser(Long id, UsersDto dto) throws InvocationTargetException, IllegalAccessException {
        Users user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        BeanUtils.copyProperties(user, dto);
        userRepository.save(user);
        return usersMapper.toDto(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {

    }

    @Transactional
    @Override
    public List<UsersDto> findUsersByBirthDate(LocalDate from, LocalDate to) {
        return null;
    }
}
