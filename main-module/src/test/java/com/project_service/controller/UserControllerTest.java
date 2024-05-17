package com.project_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.dto.UpdatePasswordDto;
import com.project_service.dto.UserCredentialsDto;
import com.project_service.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
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

    @Test
    void create() throws Exception {
        //given
        UserDto userDto = getUserDto();
        String userStr = objectMapper.writeValueAsString(userDto);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .with(SecurityMockMvcRequestPostProcessors.anonymous())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userStr))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    //then
    @Test
    @WithUserDetails(value = "aloha.test@gmail.com")
    void auth() throws Exception {

        UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setEmail("aloha.test@gmail.com");
        userCredentialsDto.setPassword("P@ssword1");

        String userCredentialsStr = objectMapper.writeValueAsString(userCredentialsDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCredentialsStr))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "eusebiujacot2000@gmail.com")
    void activateUser() throws Exception {

        String activationCode = "345543";
        mockMvc.perform(MockMvcRequestBuilders.put("/users/activate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(activationCode))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "eusebiujacot2000@gmail.com")
    void resendActivationCode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/users/resend_email"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "eusebiujacot2000@gmail.com")
    void resetPassword() throws Exception {
        String email = "eusebiujacot2000@gmail.com";
        mockMvc.perform(MockMvcRequestBuilders.put("/users/forgot_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(email))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "eusebiujacot2000@gmail.com")
    void updatePassword() throws Exception {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setEmail("eusebiujacot2000@gmail.com");
        updatePasswordDto.setResetCode("345543");
        updatePasswordDto.setNewPassword("NewPassword1");

        String updatePasswordRequest = objectMapper.writeValueAsString(updatePasswordDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/change_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePasswordRequest))
                .andExpect(status().isOk());
    }

    private static UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail("eusebiujacot@gmail.com");
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
