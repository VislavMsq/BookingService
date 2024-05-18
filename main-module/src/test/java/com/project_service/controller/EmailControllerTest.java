package com.project_service.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class EmailControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "eusebiujacot2000@gmail.com")
    void resendActivationCode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/emails/resend_activation"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "eusebiujacot2000@gmail.com")
    void resetPassword() throws Exception {
        String email = "eusebiujacot2000@gmail.com";
        mockMvc.perform(MockMvcRequestBuilders.put("/emails/forgot_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(email))
                .andExpect(status().isOk());
    }
}
