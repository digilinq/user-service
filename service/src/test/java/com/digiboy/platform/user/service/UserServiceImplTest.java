package com.digiboy.platform.user.service;

import com.digiboy.platform.user.api.UserService;
import com.digiboy.platform.user.dto.UserDTO;
import com.digiboy.platform.user.mapper.UserMapperImpl;
import com.digiboy.platform.user.repository.UserRepository;
import com.digiboy.platform.user.to.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserServiceImpl.class, UserMapperImpl.class})
class UserServiceImplTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserService userService;

    @MockBean
    Logger logger;

    @MockBean
    UserRepository userRepository;

    @Test
    void loadContext() {
        assertNotNull(applicationContext);
    }

    @Test
    void printContext() {
        System.out.println("---".repeat(24));
        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);
        System.out.println("---".repeat(24));
    }


    @Test
    void injectUserService() {
        assertNotNull(userService);
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    void shouldThrowInvalidArgumentException_whenUsernameIsEmpty(String username) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);

        assertThrows(IllegalArgumentException.class, () -> userService.save(userDTO));
    }

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Test
    void shouldSendUsernameWithLowercase_whenSaveUserAsEntity() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("IntelliJ");
        userService.save(userDTO);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        assertEquals("intellij", userArgumentCaptor.getValue().getUsername());
    }
}