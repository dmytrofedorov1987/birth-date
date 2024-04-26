package com.example.birthdate.repository;

import com.example.birthdate.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT u FROM Users u WHERE u.birthDate > :from AND u.birthDate < :to")
    List<Users> findUsersByBirthDate(@Param("from") LocalDate from, @Param("to") LocalDate to);

}

