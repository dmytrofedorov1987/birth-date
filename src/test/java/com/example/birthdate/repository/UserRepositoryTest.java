package com.example.birthdate.repository;

import com.example.birthdate.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;
    private Users user1;
    private Users user2;
    private Users user3;

    @BeforeEach
    public void setupObjects() {
        LocalDate date1 = LocalDate.of(1990, 12, 03);
        user1 = new Users("1@gmail.com", "Dima", "D", date1, "Ukraine", "+380111111111");
        user1.setId(1L);
        usersRepository.save(user1);
        LocalDate date2 = LocalDate.of(1993, 03, 15);
        user2 = new Users("2@gmail.com", "Alex", "A", date2, "Ukraine", "+380222222222");
        user2.setId(2L);
        usersRepository.save(user2);
        LocalDate date3 = LocalDate.of(1995, 10, 22);
        user3 = new Users("3@gmail.com", "Roman", "R", date3, "Ukraine", "+380333333333");
        user3.setId(3L);
        usersRepository.save(user3);
    }

    @Test
    public void testExistsUsersByEmail() {
        boolean receiveResult = usersRepository.existsByEmail("1@gmail.com");
        assertTrue(receiveResult);
    }

    @Test
    public void testExistsUsersById() {
        Long id = user2.getId();
        boolean receiveResult = usersRepository.existsById(id);
        assertTrue(receiveResult);
    }

    @Test
    public void testFindUsersByBirthDate() {
        LocalDate from = LocalDate.of(1980, 01, 01);
        LocalDate to = LocalDate.of(1993, 05, 01);
        List<Users> actualList = usersRepository.findUsersByBirthDate(from, to, PageRequest.of(0, 5));
        List<Users> expectedList = List.of(user1, user2);

        assertThat(actualList).isNotEmpty();
        assertEquals(expectedList.get(0).getId(), actualList.get(0).getId());
        assertEquals(expectedList.get(1).getId(), actualList.get(1).getId());
    }

    @Test
    public void testCountUsersByBirthDate() {
        LocalDate from = LocalDate.of(1992, 01, 01);
        LocalDate to = LocalDate.of(1997, 01, 01);
        List<Users> actualList = usersRepository.findUsersByBirthDate(from, to, PageRequest.of(0, 5));

        assertThat(actualList).isNotEmpty();
        assertEquals(2, actualList.size());
    }

}
