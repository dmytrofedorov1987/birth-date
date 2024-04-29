package com.example.birthdate.mapper;

import com.example.birthdate.dto.UsersDto;
import com.example.birthdate.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Interface allows update not all fields in Users.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdateMapper {

    void update(@MappingTarget Users user, UsersDto dto);

}
