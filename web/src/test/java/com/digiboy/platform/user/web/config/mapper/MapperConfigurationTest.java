package com.digiboy.platform.user.web.config.mapper;

import com.digiboy.platform.user.generated.v1.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        MapperConfiguration.class
})
class MapperConfigurationTest {

    @Autowired
    ObjectMapper mapper;

    @Test
    void itShouldWork() throws JsonProcessingException {
        User user = new User().userId(UUID.randomUUID());
        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
    }
}