package com.example.birthdate.service;

import com.example.birthdate.dto.UsersDto;
import com.example.birthdate.exception.BirthDateRuntimeException;
import com.example.birthdate.mapper.UsersMapper;
import com.example.birthdate.model.Users;
import com.example.birthdate.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService implements UsersServiceInterface {
    private final ObjectMapper objectMapper;
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
    public UsersDto updateUser(Long id, UsersDto dto) throws JsonProcessingException {
        Users user = userRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(user, JsonNode.class));

         userRepository.save(objectMapper.treeToValue(patched, Users.class));
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
