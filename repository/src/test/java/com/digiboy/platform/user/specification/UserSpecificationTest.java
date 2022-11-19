package com.digiboy.platform.user.specification;

import com.digiboy.platform.user.repository.UserRepository;
import com.digiboy.platform.user.to.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;

import static com.digiboy.platform.user.specification.SpecificationBuilder.build;
import static com.digiboy.platform.user.specification.UserSpecification.emailEqualsIgnoreCase;
import static com.digiboy.platform.user.specification.UserSpecification.usernameEqualsIgnoreCase;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserSpecificationTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Sql("classpath:sql/users.sql")
    void shouldReturnSingleUser_whenSearchByUsernameAndEmail() {
        assertEquals(2, userRepository.count());

        assertEquals(1, userRepository.findAll(emailEqualsIgnoreCase("info@example.com")).size());
        assertEquals(1, userRepository.findAll(usernameEqualsIgnoreCase("u01")).size());
        assertEquals(1, userRepository.findAll(
                emailEqualsIgnoreCase("info@example.com").and(usernameEqualsIgnoreCase("u01"))
        ).size());
    }

    @Test
    @Sql("classpath:sql/users.sql")
    void searchByCombinationOfCriteria() {
        assertEquals(2, userRepository.count());

        assertEquals(1, userRepository.findAll(
                build(usernameEqualsIgnoreCase("u02"), emailEqualsIgnoreCase("team@example.com"))
        ).size());
    }
}