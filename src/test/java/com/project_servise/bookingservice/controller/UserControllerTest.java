package com.project_servise.bookingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_servise.bookingservice.dto.UserDto;
import com.project_servise.bookingservice.entity.User;
import com.project_servise.bookingservice.entity.enums.Role;
import com.project_servise.bookingservice.security.CustomUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

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
    private PasswordEncoder passwordEncoder;

    @Test
    void create() throws Exception {
        //given
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

        String userStr = objectMapper.writeValueAsString(userDto);

        //when
        String id = mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .with(SecurityMockMvcRequestPostProcessors.anonymous())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userStr))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        User user = new User();
        user.setId(UUID.fromString(id));
        user.setRole(Role.OWNER);
        CustomUserDetails userDetails = new CustomUserDetails(user);

        String userJson = mockMvc.perform(MockMvcRequestBuilders.get("/users/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user(userDetails)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        UserDto actualUser = objectMapper.readValue(userJson, UserDto.class);

        Assertions.assertNotNull(actualUser.getId());
        Assertions.assertEquals(Role.OWNER.toString(), actualUser.getRole());
        Assertions.assertNull(actualUser.getOwnerId());
        Assertions.assertTrue(passwordEncoder.matches(userDto.getPassword(), actualUser.getPassword()));
        actualUser.setId(null);
        actualUser.setPassword(userDto.getPassword());
        actualUser.setRole(null);
        Assertions.assertEquals(userDto, actualUser);
    }
}