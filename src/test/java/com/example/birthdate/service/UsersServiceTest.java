package com.example.birthdate.service;

import com.example.birthdate.dto.UsersDto;
import com.example.birthdate.exception.BirthDateRuntimeException;
import com.example.birthdate.mapper.UpdateMapper;
import com.example.birthdate.mapper.UsersMapper;
import com.example.birthdate.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @Mock
    private UsersRepository repository;
    @Mock
    private UsersMapper usersMapper;
    @Mock
    private UpdateMapper updateMapper;
    @InjectMocks
    private UsersService service;

    private UsersDto usersDto;

    @BeforeEach
    public void setupObject() {
        LocalDate date = LocalDate.of(1990, 12, 03);
        usersDto = new UsersDto("1@gmail.com", "Dima", "D", date, "Ukraine", "+380111111111");
    }

    @Test
    public void testCreateUser_returnDto() {
        LocalDate date = LocalDate.of(1990, 12, 03);
        UsersDto expectUsersDto = new UsersDto(1L, "1@gmail.com", "Dima", "D", date, "Ukraine", "+380111111111");

        when(service.createUser(usersDto)).thenReturn(expectUsersDto);

        UsersDto actualUser = service.createUser(usersDto);

        assertEquals(expectUsersDto, actualUser);
    }

    @Test
    public void testCreateUsers_returnException() {
        when(service.createUser(usersDto)).thenThrow(new BirthDateRuntimeException("User with email " + usersDto.getEmail() + " exists."));

        BirthDateRuntimeException exception = assertThrows(BirthDateRuntimeException.class, () -> {
            service.createUser(usersDto);
        });

        assertEquals("User with email " + usersDto.getEmail() + " exists.", exception.getMessage());
    }

}
