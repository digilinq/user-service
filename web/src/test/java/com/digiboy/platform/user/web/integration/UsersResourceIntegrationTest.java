package com.digiboy.platform.user.web.integration;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
//@AutoConfigureCache
//@AutoConfigureDataJpa
//@AutoConfigureTestDatabase
//@AutoConfigureTestEntityManager
//@ImportAutoConfiguration

@IntegrationTest
class UsersResourceIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldRejectWithBadRequest_whenRequestWithMalformedEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/{email}/email", "incorrect-email"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRejectWithNotFound_whenUserNotFound() throws Exception {

        final String email = "info@example.com";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/{email}/email", email))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Sql("classpath:sql-data/insert-single-user.sql")
    void shouldFindUser() throws Exception {

        final String email = "info@example.com";

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/{email}/email", email))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("04017bf8-cadc-11ec-a70f-acde48001122")))
                .andReturn().getResponse();

        assertEquals(MediaType.APPLICATION_JSON, response.getContentType());
    }
}
