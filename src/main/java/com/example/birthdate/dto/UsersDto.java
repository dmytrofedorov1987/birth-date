package com.example.birthdate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class UsersDto {

    private Long id;
    @NotBlank(groups = Save.class)
    @Email(message = "Email should be valid and unique.", groups = {Save.class, Update.class})
    private String email;
    @NotBlank(message = "User First name cannot be empty", groups = Save.class)
    private String firstName;
    @NotBlank(message = "User Last name cannot be empty", groups = Save.class)
    private String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date must be before current date.")
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;

    /**
     * Checking for Save
     */
    public interface Save {
    }

    /**
     * Checking for Update
     */
    public interface Update {
    }
}
