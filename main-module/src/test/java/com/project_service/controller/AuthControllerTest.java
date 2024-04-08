package com.project_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.dto.UserCredentialsDto;
import com.project_service.security.jwt.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Test
    void auth() throws Exception {
        // given
        UserCredentialsDto credentialsDto = new UserCredentialsDto();
        credentialsDto.setEmail("aloha.test@gmail.com");
        credentialsDto.setPassword("P@ssword1");

        String userDtoStr = objectMapper.writeValueAsString(credentialsDto);

        // when
        String securityToken = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoStr))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        String actualEmail = jwtService.getEmailFromToken(securityToken);

        Assertions.assertEquals(credentialsDto.getEmail(), actualEmail);
    }
}
