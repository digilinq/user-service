package com.digiboy.platform.user.web.resources;

import com.digiboy.platform.user.api.UserService;
import com.digiboy.platform.user.generated.v1.model.CreateUserRequest;
import com.digiboy.platform.user.web.config.mapper.MapperConfiguration;
import com.digiboy.platform.user.web.mapper.CreateUserMapperImpl;
import com.digiboy.platform.user.web.mapper.EncryptedPasswordMapper;
import com.digiboy.platform.user.web.mapper.UserInfoMapperImpl;
import com.digiboy.platform.user.web.mapper.UserModelMapperImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.InputStream;
import java.util.Set;

@WebMvcTest
@Import({
        UserModelMapperImpl.class,
        CreateUserMapperImpl.class,
        UserInfoMapperImpl.class,
        MapperConfiguration.class,
        EncryptedPasswordMapper.class
})
class UsersResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService service;

    @MockBean
    private Logger logger;

    @TestConfiguration
    static class SpringContextConfiguration {
        @Bean
        PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = "mock-data/create-user.json")
    void itShouldWork(String resourceName) throws Exception {
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
            CreateUserRequest request = mapper.readValue(in, CreateUserRequest.class);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsBytes(request)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(ResultMatcher.matchAll(
                            MockMvcResultMatchers.status().isCreated()
                    ));
        }
    }

    @Autowired
    Validator validator;

    @ParameterizedTest
    @ValueSource(strings = "mock-data/invalid-requests/create-user-request-empty-confirm-password.json")
    void invalidRequest(String resourceName) throws Exception {
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
            CreateUserRequest request = mapper.readValue(in, CreateUserRequest.class);
            Set<ConstraintViolation<CreateUserRequest>> constraintViolations = validator.validate(request);
            if (!constraintViolations.isEmpty()) {
                constraintViolations.stream().map(constraintViolation -> String.format("%s: '%s' %s",
                        constraintViolation.getPropertyPath(),
                        constraintViolation.getInvalidValue(),
                        constraintViolation.getMessage())
                ).forEach(System.out::println);
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "mock-data/create-user-email-malformed.json",
            "mock-data/create-user-empty-email.json",
    })
    void itWillReject(String resourceName) throws Exception {
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
            CreateUserRequest request = mapper.readValue(in, CreateUserRequest.class);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsBytes(request)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(ResultMatcher.matchAll(
                            MockMvcResultMatchers.status().isBadRequest()));
        }
    }
}