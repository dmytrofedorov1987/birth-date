package com.example.birthdate.service;

import com.example.birthdate.dto.UsersDto;
import com.example.birthdate.exception.BirthDateRuntimeException;
import com.example.birthdate.mapper.UsersMapper;
import com.example.birthdate.model.Users;
import com.example.birthdate.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
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
        Users user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Converter converter = new DateConverter(null);
        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
        beanUtilsBean.getConvertUtils().register(converter, Date.class);
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
