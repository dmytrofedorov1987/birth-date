package com.example.birthdate.mapper;

import com.example.birthdate.dto.UsersDto;
import com.example.birthdate.model.Users;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Interface for transfer entity to DTO.
 */
@Mapper(componentModel = "spring")
public interface UsersMapper {

    Users toEntity(UsersDto dto);

    UsersDto toDto(Users user);

    List<UsersDto> toDto(List<Users> usersList);

}
