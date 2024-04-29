package com.example.birthdate.service;

import com.example.birthdate.dto.UsersDto;
import com.example.birthdate.exception.BirthDateRuntimeException;
import com.example.birthdate.mapper.UpdateMapper;
import com.example.birthdate.mapper.UsersMapper;
import com.example.birthdate.model.Users;
import com.example.birthdate.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService implements UsersServiceInterface {

    private final UsersRepository userRepository;
    private final UsersMapper usersMapper;
    private final UpdateMapper updateMapper;

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
    public UsersDto updateUser(Long id, UsersDto dto) {
        Users user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        updateMapper.update(user, dto);
        userRepository.save(user);
        return usersMapper.toDto(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    @Override
    public List<UsersDto> findUsersByBirthDate(LocalDate from, LocalDate to, Pageable pageable) {
        List<Users> users = userRepository.findUsersByBirthDate(from, to, pageable);
        return usersMapper.toDto(users);
    }

    @Transactional
    @Override
    public int totalPages(LocalDate from, LocalDate to, int size) {
        int countUsers = userRepository.countUsers(from, to);
        return (countUsers / size) + (countUsers % size > 0 ? 1 : 0);
    }


}
