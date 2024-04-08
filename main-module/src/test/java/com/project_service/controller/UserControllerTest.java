package com.project_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.dto.UserCredentialsDto;
import com.project_service.dto.UserDto;
import com.project_service.security.jwt.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Test
    void create() throws Exception {
        //given
        UserDto userDto = getUserDto();
        String userStr = objectMapper.writeValueAsString(userDto);

        //when
        String securityToken = mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .with(SecurityMockMvcRequestPostProcessors.anonymous())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userStr))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        String actualEmail = jwtService.getEmailFromToken(securityToken);
        Assertions.assertEquals(userDto.getEmail(), actualEmail);

        UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setEmail(userDto.getEmail());
        userCredentialsDto.setPassword(userDto.getPassword());

        String userCredentialsStr = objectMapper.writeValueAsString(userCredentialsDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCredentialsStr))
                .andExpect(status().isOk());
    }

    private static UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail("ivan.ivanov@gmail.com");
        userDto.setPassword("P@ssword1");
        userDto.setCurrencyCode("CNY");
        userDto.setFirstName("Ivan");
        userDto.setLastName("Ivanov");
        userDto.setPhone("+48123456789");
        userDto.setCountry("Poland");
        userDto.setLanguage("Russian");
        userDto.setTimezone(2);
        return userDto;
    }
}
