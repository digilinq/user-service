package com.digiboy.platform.user.repository;

import com.digiboy.platform.user.specification.UserSpecification;
import com.digiboy.platform.user.to.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void userIdShouldGenerated() {
        User user = new User();
        UUID userId = UUID.randomUUID();
        user.setUserId(userId);
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getUserId()).isNotNull();
        assertThat(savedUser.getUserId()).isNotEqualTo(userId);
    }

    @Test
    void usernameIsUnique() {
        User user1 = new User();
        user1.setUsername("username");
        User user2 = new User();
        user2.setUsername("username");
        userRepository.save(user1);
        userRepository.save(user2);

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.flush());
    }

    @Test
    void itIsPossibleToSaveToUserWithEmptyUsername() {
        User user1 = new User();
        User user2 = new User();
        userRepository.save(user1);
        userRepository.save(user2);
        assertDoesNotThrow(() -> userRepository.flush());
    }

    @Test
    void searchByEmailShouldNotCaseSensitive() {
        String email = "hmohammadi@heXaware.com";
        User user = new User();
        user.setEmail(email);
        userRepository.save(user);
        // repository.flush();
        assertThat(userRepository.findAll(UserSpecification.emailEqualsIgnoreCase("hmohammadi@hexaWare.com")).size()).isEqualTo(1);
    }

    @Test
    @Sql("classpath:sql/users.sql")
    void findUserByEmailExpectToFindSingleResponseOtherwiseNull() {
        assertThat(
                assertDoesNotThrow(() -> userRepository.findByEmail("manager@example.com"))
        ).isNull();
        User user = userRepository.findByEmail("info@example.com");
        assertThat(user.getUsername()).isEqualTo("u01");
        assertThat(user.getPassword()).isEqualTo("P@ssw0rd");
    }
}